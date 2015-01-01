授業の課題

![alt tag](https://raw.githubusercontent.com/iinm/news-search/master/screenshot.png)

---
index

```
% lein run -m news-search.script.tf
% lein run -m news-search.script.df
% lein run -m news-search.script.tfidf
% lein run -m news-search.script.inverted-index
```

---
test

```
% lein run -m news-search.script.search-cache 中国 鉄道
[検索結果] 27 件見つかりました．
-----------------------------
990324093       (0.3918164898660778)
990826083       (0.32292244320882457)
990303100       (0.3166661512619297)
990406085       (0.30330620725901575)
991028011       (0.2975596548706861)
...
```

```
% lein run -m news-search.script.search-cache 中国鉄道
[検索結果] 3 件見つかりました．
-----------------------------
990324093       (0.3918164898660778)
990826083       (0.32292244320882457)
990303100       (0.3166661512619297)
```
