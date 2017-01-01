
var React = require('react');
var ReactDOM = require('react-dom');

var h1 = React.createElement('h1', { className: 'header' }, 'This is React');
var p = React.createElement('p', { className: 'content' }, "and that's how it works.");
var fragment = [h1, p]

var section = React.createElement('section', {className: 'container'}, fragment);

ReactDOM.render(section, document.getElementById('react-application'));

