$(function() {

  var Item = Backbone.Model.extend({

    defaults: function() {
      return {
        score: 0,
        head: "",
        text: "", // full text
        show: false
      };
    },

    initialize: function() {
      _.bindAll(this, 'toggle');
    },

    toggle: function() {
      this.set({show: !this.get("show")});
    }
  });

  var List = Backbone.Collection.extend({
    models: Item,

    //defaults: function() {
    //  return {
    //    updating: false
    //  };
    //},

    initialize: function() {
      _.bindAll(this, 'setData', 'update', 'reload');
      this.on('change:property', this.reload);
    },

    reload: function() {
      console.log('reload');
      items = this.items;
      this.reset();
      _.each(items, this.setData);
    },

    setData: function(doc) {
      //console.log(doc);
      var item = new Item(doc);
      item.set({head: item.get('text').slice(0, 50),
                score: Math.round(item.get('score')*100)/100});
      this.add(item);
    },

    update: function(q) {
      this.reset();
      console.log('input: ' + q);
      setData = this.setData;
      $.get('search', {q: q},
          function(data) {
            _.each(data, setData);
          });
    }

  });

  var ItemView = Backbone.View.extend({
    //el: $('body'),
    tagName: 'div',

    headTemplate: _.template($('#head-template').html()),
    bodyTemplate: _.template($('#body-template').html()),

    events: {
      'click .panel-heading': 'show'
    },

    initialize: function() {
      _.bindAll(this, 'render', 'show');
      this.listenTo(this.model, 'change', this.render);
    },

    show: function() { // 思い通りに動いていない．
      this.model.toggle();
      this.render();
    },

    render: function() {
      html = this.headTemplate(this.model.toJSON());
      //if (this.model.get('show'))
        html += this.bodyTemplate(this.model.toJSON());
      this.$el.html(html);
      return this;
    }
  });

  var ListView = Backbone.View.extend({

    el: $('body'), // ????? bodyじゃないと動かない．

    events: {
      'click button#go': 'search',
      'keypress input#q': 'searchOnEnter'
    },

    initialize: function() {
      _.bindAll(this, 'render', 'search', 'appendItem', 'searchOnEnter');
      this.collection = new List();
      this.listenTo(this.collection, 'add', this.render);
      //this.listenTo(this.collection, 'reset', this.render);
    },

    render: function() {
      if (_.isEmpty(this.collection.models)) {
        numFound = 0;
      } else {
        numFound = this.collection.models.length;
      }

      $('#results').html('');

      //if (this.collection.updating) {
      //  $('#results').append('<p>検索中...</p>');

      //} else {
        $('#results').append('<p>' + numFound +
            ((numFound >= 25) ? '+' : '') +
            ' 件見つかりました．</p>');
        if (numFound != 0)
          $('#results').append('<div id="panel" class="panel panel-default"></div>');
      //}

      var self = this;
      _.each(this.collection.models,
          function(item) {
            self.appendItem(item);
          }, this);
    },

    input: $('input#q'),

    search: function() {
      q = this.input.val().trim();
      this.collection.update(q);
    },

    searchOnEnter: function(e) {
      if (e.keyCode != 13) return;
      if (!this.input.val().trim()) return;
      this.search();
    },

    appendItem: function(item) {
      var itemView = new ItemView({model: item});
      $('div#panel').append(itemView.render().el.children);
    }
  });

  var ListView = new ListView();

});
