const React = require("react");

class Header extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {
        return <h2 className="header">{this.props.text}</h2>;
    }
}

module.exports = Header;