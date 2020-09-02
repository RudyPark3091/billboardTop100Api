const express = require("express");
const app = express();
const fetch = require("node-fetch");
const { JSDOM } = require("jsdom");

const apiData = [];

fetch("https://www.billboard.com/charts/hot-100")
  .then((response) => {
    return response.text();
  })
  .then((html) => {
    const parsedHtml = new JSDOM(html);

    const song = [];
    const artist = [];
    const rank = [];
    for (let i = 0; i < 100; i++) {
      const chartList = new JSDOM(
        parsedHtml.window.document.querySelector(
          `#charts > div > div.chart-list.container > ol > li:nth-child(${
            i + 1
          })`
        ).innerHTML
      );

      song.push(
        chartList.window.document.querySelector(
          "button > span.chart-element__information > span.chart-element__information__song.text--truncate.color--primary"
        ).innerHTML
      );
      artist.push(
        chartList.window.document.querySelector(
          "button > span.chart-element__information > span.chart-element__information__artist.text--truncate.color--secondary"
        ).innerHTML
      );
      rank.push(
        chartList.window.document.querySelector(
          "button > span.chart-element__rank.flex--column.flex--xy-center.flex--no-shrink > span.chart-element__rank__number"
        ).innerHTML
      );
    }

    const data = {
      song,
      artist,
      rank
    };

    return data;
  })
  .then((data) => {
    for (let i = 0; i < 100; i++) {
      let subData = {};
      subData.rank = data.rank[i];
      subData.song = data.song[i];
      subData.artist = data.artist[i];
      apiData.push(subData);
    }
  })
  .catch((error) => {
    console.warn("error!", error);
  });

app.get("/", (req, res) => {
  res.send(
    "go to /{rank number you want to know} and you'll see 1~n rank of billboard chart"
  );
});

app.get("/:rank", (req, res) => {
  const rank = req.params.rank;
  const apiDataToShow = [];
  for (let i = 0; i < rank; i++) {
    apiDataToShow.push(apiData[i]);
  }
  res.send(apiDataToShow);
});

app.listen(8080, () => {
  console.log("server ready");
});
