'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = build;

var _webpack = require('webpack');

var _webpack2 = _interopRequireDefault(_webpack);

var _path = require('path');

var _path2 = _interopRequireDefault(_path);

var _fs = require('fs');

var _fs2 = _interopRequireDefault(_fs);

var _LogUtils = require('./LogUtils');

var _Constants = require('./Constants');

var _PackageUtils = require('./PackageUtils');

var _ProgressPlugin = require('webpack/lib/ProgressPlugin');

var _ProgressPlugin2 = _interopRequireDefault(_ProgressPlugin);

var _babelCore = require('babel-core');

var _shelljs = require('shelljs');

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var WEBPACK_PATH = _path2.default.join(_Constants.PUBLIC_DIR, 'webpack.config.js'); /* eslint no-console: 0 */


function build(cb) {
  (0, _LogUtils.log)('NODE_ENV=' + process.env.NODE_ENV);
  validateEnv();
  transpileWebpackConfig(function () {
    bundleServer(function () {
      bundleClient(cb);
    });
  });
}

function transpileWebpackConfig(cb) {
  var configPath = _path2.default.join(_Constants.APP_PATH, (0, _PackageUtils.getDXConfig)().webpack);
  var options = JSON.parse(_fs2.default.readFileSync(_path2.default.join(_Constants.APP_PATH, '.babelrc')));
  (0, _babelCore.transformFile)(configPath, options, function (err, result) {
    if (err) throw err;
    (0, _shelljs.mkdir)('-p', _Constants.PUBLIC_DIR);
    _fs2.default.writeFileSync(WEBPACK_PATH, result.code);
    cb();
  });
}

function validateEnv() {
  if (!process.env.NODE_ENV) {
    (0, _LogUtils.logError)('OOPS', 'NODE_ENV is undefined, please add it to your `.env` file');
    console.log();
    console.log('  NODE_ENV=development');
    console.log();
    process.exit();
  }
}

function getAppWebpackConfig() {
  return require(WEBPACK_PATH);
}

function bundleClient(cb) {
  (0, _LogUtils.log)('bundling client');
  bundle(getAppWebpackConfig().ClientConfig, { saveStats: true }, cb);
}

function bundleServer(cb) {
  (0, _LogUtils.log)('bundling server');
  bundle(getAppWebpackConfig().ServerConfig, { saveStats: false }, cb);
}

function bundle(config, _ref, cb) {
  var saveStats = _ref.saveStats;

  var compiler = (0, _webpack2.default)(config);
  compiler.apply(new _ProgressPlugin2.default(function (percentage, msg) {
    if (!msg.match(/build modules/)) (0, _LogUtils.log)('[webpack]', msg);
  }));
  compiler.run(function (err, rawStats) {
    if (err) {
      throw err;
    } else {
      var stats = rawStats.toJson();
      if (stats.errors.length) {
        throw stats.errors[0];
      } else {
        if (saveStats) {
          var statsPath = config.output.path + '/stats.json';
          _fs2.default.writeFileSync(statsPath, JSON.stringify(stats, null, 2));
          (0, _LogUtils.log)('wrote file', _path2.default.relative(_Constants.APP_PATH, statsPath));
        }
        cb();
      }
    }
  });
}