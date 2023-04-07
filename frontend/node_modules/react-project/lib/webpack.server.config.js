'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _webpack = require('webpack');

var _webpack2 = _interopRequireDefault(_webpack);

var _fs = require('fs');

var _fs2 = _interopRequireDefault(_fs);

var _path = require('path');

var _path2 = _interopRequireDefault(_path);

var _webpackShared = require('./webpack.shared.config');

var SHARED = _interopRequireWildcard(_webpackShared);

var _Constants = require('./Constants');

var _PackageUtils = require('./PackageUtils');

function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } else { var newObj = {}; if (obj != null) { for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) newObj[key] = obj[key]; } } newObj.default = obj; return newObj; } }

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var PROD = process.env.NODE_ENV === 'production';

function getPublicPath() {
  return PROD ? _Constants.PUBLIC_PATH : 'http://' + _Constants.DEV_HOST + ':' + _Constants.DEV_PORT + '/';
}

var nodeModules = _fs2.default.readdirSync(_path2.default.join(SHARED.APP_PATH, 'node_modules')).filter(function (x) {
  return ['.bin'].indexOf(x) === -1;
});

exports.default = {

  target: 'node',

  devtool: 'sourcemap',

  entry: _path2.default.resolve(SHARED.APP_PATH, (0, _PackageUtils.getDXConfig)().server),

  output: {
    path: _path2.default.join(SHARED.APP_PATH, '.build'),
    filename: 'server.js',
    publicPath: getPublicPath()
  },

  externals: getExternals(),

  node: {
    __filename: true,
    __dirname: true
  },

  module: {
    loaders: [{ test: SHARED.JSON_REGEX,
      loader: 'json-loader'
    }, { test: SHARED.FONT_REGEX,
      loader: 'url-loader?limit=10000'
    }, { test: SHARED.IMAGE_REGEX,
      loader: 'url-loader?limit=10000'
    }, { test: SHARED.JS_REGEX,
      loader: 'babel-loader',
      exclude: /node_modules/
    }, { test: SHARED.CSS_REGEX,
      loader: 'css-loader/locals?' + SHARED.CSS_LOADER_QUERY + '!postcss-loader'
    }]
  },

  plugins: [new _webpack2.default.BannerPlugin('require("source-map-support").install();', { raw: true, entryOnly: false }), new _webpack2.default.ProvidePlugin({
    fetch: 'node-fetch'
  })]

};


function getExternals() {
  // keep node_module paths out of the bundle
  return [function (context, request, callback) {
    var pathStart = request.split('/')[0];
    // can't remember why we need to bundle up react-project stuff ...
    if (nodeModules.indexOf(pathStart) >= 0 && request !== 'react-project' && request !== 'react-project/server') {
      callback(null, 'commonjs ' + request);
    } else {
      callback();
    }
  }];
}