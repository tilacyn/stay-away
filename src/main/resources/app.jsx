const ReactDOM = require("react-dom/client");
const React = require("react");
import Header from "./components/Header/header.jsx";
import Article from "./components/Article/article.jsx";
import "./styles.jsx";

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