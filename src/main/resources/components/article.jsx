const React = require("react");

class Article extends React.Component{

    constructor(props){
        super(props);
    }
    render() {
        return <div>{this.props.content}</div>;
    }
}

module.exports = Article;