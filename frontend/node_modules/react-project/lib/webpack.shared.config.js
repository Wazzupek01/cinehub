'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.CSS_LOADER_QUERY = exports.JS_EXCLUDE_REGEX = exports.JSON_REGEX = exports.JS_REGEX = exports.CSS_REGEX = exports.IMAGE_REGEX = exports.FONT_REGEX = exports.NODE_ENV = exports.APP_ENTRIES_PATH = exports.CLIENT_ENTRY = exports.APP_PATH = undefined;

var _path = require('path');

var _path2 = _interopRequireDefault(_path);

var _Constants = require('./Constants');

var _PackageUtils = require('./PackageUtils');

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.APP_PATH = _Constants.APP_PATH;
var CLIENT_ENTRY = exports.CLIENT_ENTRY = _path2.default.join(_Constants.APP_PATH, (0, _PackageUtils.getDXConfig)().client);
var APP_ENTRIES_PATH = exports.APP_ENTRIES_PATH = _path2.default.join(_Constants.APP_PATH, 'modules');
var NODE_ENV = exports.NODE_ENV = process.env.NODE_ENV;
var FONT_REGEX = exports.FONT_REGEX = /\.(otf|eot|svg|ttf|woff|woff2).*$/;
var IMAGE_REGEX = exports.IMAGE_REGEX = /\.(gif|jpe?g|png|ico)$/;
var CSS_REGEX = exports.CSS_REGEX = /\.css$/;
var JS_REGEX = exports.JS_REGEX = /\.js$/;
var JSON_REGEX = exports.JSON_REGEX = /\.json$/;
var JS_EXCLUDE_REGEX = exports.JS_EXCLUDE_REGEX = /node_modules/;
var CSS_LOADER_QUERY = exports.CSS_LOADER_QUERY = 'modules&importLoaders=1&localIdentName=[name]__[local]___[hash:base64:5]';