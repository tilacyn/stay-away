const ReactDOM = require("react-dom/client");
const React = require("react");
const Header = require("./components/header.jsx");
const Article = require("./components/article.jsx");

const header = "Рассказ";
const article = "После одного из заседаний N-ского мирового съезда судьи собрались в совещательной комнате, чтобы снять свои мундиры, минутку отдохнуть и ехать домой обедать.";

ReactDOM.createRoot(
    document.getElementById("app")
)
    .render(
        <div>
            <Header text={header} />
            <Article content={article} />
        </div>
    );