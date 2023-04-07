'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.AUTO_RELOAD = exports.SERVER_RENDERING = exports.PUBLIC_DIR = exports.PUBLIC_PATH = exports.DEV_PORT = exports.DEV_HOST = exports.PORT = exports.APP_PATH = undefined;

var _path = require('path');

var _path2 = _interopRequireDefault(_path);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var APP_PATH = exports.APP_PATH = process.cwd();
var PORT = exports.PORT = process.env.PORT || 8080;
var DEV_HOST = exports.DEV_HOST = process.env.DEV_HOST || 'localhost';
var DEV_PORT = exports.DEV_PORT = process.env.DEV_PORT || 8081;
var PUBLIC_PATH = exports.PUBLIC_PATH = process.env.PUBLIC_PATH || '/';
var PUBLIC_DIR = exports.PUBLIC_DIR = _path2.default.join(APP_PATH, '.build');
var SERVER_RENDERING = exports.SERVER_RENDERING = process.env.SERVER_RENDERING === 'on';
var AUTO_RELOAD = exports.AUTO_RELOAD = process.env.AUTO_RELOAD;