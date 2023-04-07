'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = start;

var _path = require('path');

var _path2 = _interopRequireDefault(_path);

var _webpack = require('webpack');

var _webpack2 = _interopRequireDefault(_webpack);

var _webpackDevServer = require('webpack-dev-server');

var _webpackDevServer2 = _interopRequireDefault(_webpackDevServer);

var _build = require('./build');

var _build2 = _interopRequireDefault(_build);

var _LogUtils = require('./LogUtils');

var _PackageUtils = require('./PackageUtils');

var _Constants = require('./Constants');

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

// should be created by build task already
var WEBPACK_PATH = _path2.default.join(_Constants.PUBLIC_DIR, 'webpack.config.js');

var PROD = process.env.NODE_ENV === 'production';

function getAppWebpackConfig() {
  return require(WEBPACK_PATH);
}

function start(cb) {
  validateEnv();
  if (PROD) {
    logDXStartWarning();
  } else {
    checkDependencies();
    (0, _build2.default)(function () {
      var appServerPath = _path2.default.join(process.cwd(), '.build', 'server.js');
      require(appServerPath);
      runDevServer(cb);
    });
  }
}

function validateEnv() {
  if (!PROD && _Constants.AUTO_RELOAD === 'hot' && _Constants.SERVER_RENDERING) {
    (0, _LogUtils.logError)('Hot Module Replacement is disabled because SERVER_RENDERING is enabled.');
  }
}

function checkDependencies() {
  (0, _LogUtils.log)('checking app dependencies');
  var pkg = (0, _PackageUtils.getPackageJSON)();
  var blueprintPkg = require('../create-react-project/blueprint/package.json');
  var missingDeps = [];
  var differentDeps = [];
  for (var key in blueprintPkg.dependencies) {
    var blueprintVersion = key + '@' + blueprintPkg.dependencies[key];
    var pkgVersion = key + '@' + pkg.dependencies[key];
    if (!pkg.dependencies[key]) {
      missingDeps.push(blueprintVersion);
    } else if (pkgVersion !== blueprintVersion) {
      differentDeps.push({ pkgVersion: pkgVersion, blueprintVersion: blueprintVersion });
    }
  }

  if (differentDeps.length) {
    (0, _LogUtils.log)('Some of your dependencies don\'t match what I expect');
    differentDeps.forEach(function (dep) {
      (0, _LogUtils.log)('You have: ' + dep.pkgVersion + ' and I expect ' + dep.blueprintVersion);
    });
    (0, _LogUtils.log)('You might want to `npm install` the versions I expect.');
  }

  if (missingDeps.length) {
    (0, _LogUtils.logError)('You are missing some dependencies, please run:');
    (0, _LogUtils.log)();
    (0, _LogUtils.log)('  npm install ' + missingDeps.join(' ') + ' --save --save-exact');
    (0, _LogUtils.log)();
    process.exit();
  }
}

function logDXStartWarning() {
  (0, _LogUtils.logError)('Don\'t use `react-project start` in production.');
  (0, _LogUtils.log)('First add:');
  (0, _LogUtils.log)();
  (0, _LogUtils.log)('  rm -rf .build && react-project build && node .build/server.js');
  (0, _LogUtils.log)();
  (0, _LogUtils.log)('to your package.json `scripts.start` entry, then use:');
  (0, _LogUtils.log)();
  (0, _LogUtils.log)('  npm start\n');
}

function runDevServer(cb) {
  var _getAppWebpackConfig = getAppWebpackConfig();

  var ClientConfig = _getAppWebpackConfig.ClientConfig;

  var compiler = (0, _webpack2.default)(ClientConfig);
  var server = new _webpackDevServer2.default(compiler, ClientConfig.devServer);
  server.listen(_Constants.DEV_PORT, _Constants.DEV_HOST, function () {
    (0, _LogUtils.log)('Webpack dev server listening on port', _Constants.DEV_PORT);
    cb();
  });
}