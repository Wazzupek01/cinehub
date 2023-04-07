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

var _extractTextWebpackPlugin = require('extract-text-webpack-plugin');

var _extractTextWebpackPlugin2 = _interopRequireDefault(_extractTextWebpackPlugin);

function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } else { var newObj = {}; if (obj != null) { for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) newObj[key] = obj[key]; } } newObj.default = obj; return newObj; } }

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var REFRESH = process.env.AUTO_RELOAD === 'refresh';
var PROD = process.env.NODE_ENV === 'production';
// can't do HMR with SERVER_RENDERING because of ExtractTextPlugin
var HOT = !PROD && !_Constants.SERVER_RENDERING && process.env.AUTO_RELOAD === 'hot';
var HASH = '[chunkHash]';

function getPublicPath() {
  return PROD ? _Constants.PUBLIC_PATH : 'http://' + _Constants.DEV_HOST + ':' + _Constants.DEV_PORT + '/';
}

exports.default = {

  entry: getEntry(),

  devtool: getDevTool(),

  output: {
    path: _path2.default.join(SHARED.APP_PATH, '.build'),
    filename: getFileName(),
    publicPath: getPublicPath()
  },

  module: {
    loaders: getLoaders()
  },

  plugins: getPlugins(),

  devServer: {
    hot: HOT,
    contentBase: false,
    quiet: false,
    noInfo: false,
    stats: {
      assets: true,
      version: false,
      hash: false,
      timings: false,
      chunks: false,
      chunkModules: true
    }
  }
};


function getLoaders() {
  return [getBabelLoader(), getCSSLoader(), { test: SHARED.JSON_REGEX,
    loader: 'json-loader'
  }, { test: SHARED.FONT_REGEX,
    loader: 'url-loader?limit=10000'
  }, { test: SHARED.IMAGE_REGEX,
    loader: 'url-loader?limit=10000'
  }, { test: /modules\/api\//,
    loader: 'null-loader'
  }];
}

function getCSSLoader() {
  var loader = { test: SHARED.CSS_REGEX };
  if (PROD || _Constants.SERVER_RENDERING) {
    loader.loader = _extractTextWebpackPlugin2.default.extract('style-loader', 'css-loader?' + SHARED.CSS_LOADER_QUERY + '!postcss-loader');
  } else {
    loader.loader = 'style-loader!css-loader?' + SHARED.CSS_LOADER_QUERY + '!postcss-loader';
  }
  return loader;
}

function getFileName() {
  return PROD ? HASH + '.js' : '[name].js';
}

function getDevTool() {
  return PROD ? 'sourcemap' : 'cheap-module-eval-source-map';
}

function getBabelLoader() {
  // we can't use the "dev" config in babelrc because we don't always
  // want it, sometimes we want refresh, sometimes we want none. Also, we
  // don't want it in the server bundle either (not yet anyway?)
  var loader = {
    test: SHARED.JS_REGEX,
    exclude: /node_modules/,
    loader: 'babel-loader'
  };
  if (HOT) {
    var rc = JSON.parse(_fs2.default.readFileSync(_path2.default.join(SHARED.APP_PATH, '.babelrc')));
    loader.query = { presets: rc.presets.concat(['react-hmre']) };
  }
  return loader;
}

function getEntry() {
  var entry = {
    app: _path2.default.join(SHARED.APP_PATH, (0, _PackageUtils.getDXConfig)().client),
    _vendor: ['react', 'react-dom', 'react-router', 'react-project']
  };
  if (!PROD) {
    if (HOT) {
      entry._vendor.unshift('webpack/hot/dev-server');
    }
    if (HOT || REFRESH) {
      entry._vendor.unshift('webpack-dev-server/client?http://' + _Constants.DEV_HOST + ':' + _Constants.DEV_PORT);
    }
  }
  return entry;
}

function getPlugins() {

  var plugins = [new _webpack2.default.DefinePlugin({
    'process.env': { NODE_ENV: JSON.stringify(process.env.NODE_ENV) }
  }), new _webpack2.default.optimize.CommonsChunkPlugin('_vendor', PROD ? 'vendor-' + HASH + '.js' : 'vendor.js')];

  if (PROD) {
    plugins.push(new _extractTextWebpackPlugin2.default(HASH + '.css'), new _webpack2.default.optimize.DedupePlugin(), new _webpack2.default.optimize.OccurrenceOrderPlugin(), new _webpack2.default.optimize.UglifyJsPlugin());
  } else {
    if (_Constants.SERVER_RENDERING) {
      plugins.push(new _extractTextWebpackPlugin2.default('[name].css'));
    } else if (HOT) {
      plugins.push(new _webpack2.default.HotModuleReplacementPlugin());
    }
  }

  return plugins;
}