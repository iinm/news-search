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

    //initialize: function() {
    //  _.bindAll(this, 'toggle');
    //},

    toggle: function() {
      this.set({show: !this.get("show")});
    }
  });

  var List = Backbone.Collection.extend({
    models: Item,

    update: function(q) {
      console.log('input: ' + q);
      $.get('search', {q: q},
          function(data) {
            console.log(data);
          });
    }

  });

  var ItemView = Backbone.View.extend({

    headTemplate: _.template($('#head-template').html()),
    bodyTemplate: _.template($('#body-template').html()),

    initialize: function() {
      _.bindAll(this, 'render');
    },

    render: function() {
      html = this.headTemplate(this.model.toJSON());
      if (this.model.get('show'))
        html += this.bodyTemplate(this.model.toJSON());
      return html;
    }
  });

  var ListView = Backbone.View.extend({

    el: $('body'),

    events: {
      'click button#go': 'search',
      'keypress input#q': 'searchOnEnter'
    },

    initialize: function() {
      _.bindAll(this, 'render', 'search', 'appendItem', 'searchOnEnter');
      this.collection = new List();
      this.listenTo(this.collection, 'change', this.render);
    },

    render: function() {
      if (!_.isEmpty(this.collection.models))
        $('#results').html('<div id="panel" class="panel panel-default"></div>');

      var self = this;
      _.each(this.collection.models,
          function(item) {
            self.appendItem(item);
          }, this);
    },

    input: $('input#q'),

    search: function() {
      this.collection.reset();
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
      $('div#panel').append(itemView.render());
    }
  });

  var ListView = new ListView();

});
