'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.ServerRoute = undefined;
exports.lazy = lazy;

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var _React$PropTypes = _react2.default.PropTypes;
var func = _React$PropTypes.func;
var string = _React$PropTypes.string;
var object = _React$PropTypes.object;
var oneOfType = _React$PropTypes.oneOfType;
function lazy(loader) {
  return function (location, cb) {
    loader(function (Mod) {
      cb(null, Mod.default);
    });
  };
}

var handler = oneOfType([func, object]);

var ServerRoute = exports.ServerRoute = _react2.default.createClass({
  displayName: 'ServerRoute',

  propTypes: {
    path: string.isRequired,
    get: handler,
    post: handler,
    patch: handler,
    put: handler,
    delete: handler
  },
  getDefaultProps: function getDefaultProps() {
    return {
      isServerRoute: true
    };
  },
  render: function render() {
    return null;
  }
});