'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = ErrorMessage;

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var style = {
  fontFamily: 'sans-serif',
  width: 300,
  padding: '10px 50px 50px 50px',
  background: '#f0f0f0',
  color: '#333',
  margin: '100px auto 0 auto'
};

function ErrorMessage(props) {
  return _react2.default.createElement(
    'div',
    { style: style },
    _react2.default.createElement(
      'h1',
      null,
      'React Project Error'
    ),
    props.children
  );
}