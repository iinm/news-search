授業の課題

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

---
benchmark (msec)

```
% paste -d"\t" search.txt search-ii.txt search-cache.txt
{:query-avg	{:query-avg	{:query-avg
 (["0010" 709.6747]	 (["0010" 44.04012]	 (["0010" 1.1899629]
  ["0020" 385.83575]	  ["0020" 29.831276]	  ["0020" 0.4626368]
  ["0030" 345.67737]	  ["0030" 23.558546]	  ["0030" 0.344675]
  ["0040" 359.03256]	  ["0040" 55.35002]	  ["0040" 1.2568679]
  ["0050" 355.03058]	  ["0050" 27.75068]	  ["0050" 0.3385229]
  ["0060" 354.85376]	  ["0060" 63.91002]	  ["0060" 1.3393182]
  ["0070" 354.1612]	  ["0070" 95.09087]	  ["0070" 3.1248732]
  ["0080" 355.57565]	  ["0080" 68.6174]	  ["0080" 1.4922792]
  ["0110" 344.22064]	  ["0110" 59.531197]	  ["0110" 1.4838527]
  ["0120" 353.30124]	  ["0120" 19.64883]	  ["0120" 0.3558618]
  ["0130" 367.7454]	  ["0130" 69.18]	  ["0130" 3.159174]
  ["0140" 345.74634]	  ["0140" 250.16196]	  ["0140" 9.629169]
  ["0150" 357.1185]	  ["0150" 52.352165]	  ["0150" 1.8152806]
  ["0160" 349.54886]	  ["0160" 25.910856]	  ["0160" 0.490172]
  ["0170" 350.62122]	  ["0170" 42.530483]	  ["0170" 1.0063564]
  ["0180" 349.19177]	  ["0180" 17.894672]	  ["0180" 0.2871642]
  ["0190" 358.1485]	  ["0190" 340.3385]	  ["0190" 14.291305]
  ["0200" 353.35498]	  ["0200" 70.236336]	  ["0200" 2.2653246]
  ["0210" 360.0437]	  ["0210" 69.88831]	  ["0210" 1.8063955]
  ["0220" 346.55597]	  ["0220" 49.55846]	  ["0220" 1.4467337]
  ["0230" 356.64893]	  ["0230" 39.455284]	  ["0230" 0.5810815]
  ["0240" 338.66006]	  ["0240" 29.429775]	  ["0240" 0.7503501]
  ["0250" 357.93912]	  ["0250" 153.3459]	  ["0250" 3.9620278]
  ["0260" 522.1349]	  ["0260" 46.883335]	  ["0260" 0.7559331]
  ["0270" 347.7492]	  ["0270" 54.74381]	  ["0270" 1.1013501]
  ["0280" 344.1044]	  ["0280" 35.05831]	  ["0280" 0.6374237]
  ["0290" 343.20605]	  ["0290" 37.176105]	  ["0290" 0.680502]
  ["0300" 378.38528]),	  ["0300" 349.05695]),	  ["0300" 15.5233]),
 :all-avg 373.00952}	 :all-avg 79.30465}	 :all-avg 2.5563533}
```

---
おまけ: インタフェース

![alt tag](https://raw.githubusercontent.com/iinm/news-search/master/screenshot.png)
