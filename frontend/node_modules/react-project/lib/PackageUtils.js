'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.pkgPath = undefined;
exports.getPackageJSON = getPackageJSON;
exports.getDXConfig = getDXConfig;
exports.copyProps = copyProps;

var _fs = require('fs');

var _fs2 = _interopRequireDefault(_fs);

var _path = require('path');

var _path2 = _interopRequireDefault(_path);

var _LogUtils = require('./LogUtils');

var _Constants = require('./Constants');

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

/*eslint no-console: 0*/
var pkgPath = exports.pkgPath = _path2.default.join(_Constants.APP_PATH, 'package.json');

function getPackageJSON() {
  if (!_fs2.default.existsSync(pkgPath)) logNoPackageJSON();
  var packageJSON = void 0;
  try {
    packageJSON = require(pkgPath);
  } catch (e) {
    logCantReadPackageJSON();
  }
  return packageJSON;
}

function getDXConfig() {
  var packageJSON = getPackageJSON();
  validatePackageJSON(packageJSON);
  return getPackageJSON()['react-project'];
}

function copyProps(source, target, field) {
  if (!target[field]) target[field] = {};

  for (var key in source[field]) {
    if (!target[field][key]) target[field][key] = source[field][key];
  }
}

function validatePackageJSON(appPackageJSON) {
  if (!appPackageJSON['react-project']) {
    logNoDX();
  }
  if (!appPackageJSON['react-project'] || !appPackageJSON['react-project'].client || !appPackageJSON['react-project'].server) {
    logError('No "react-project" entry found in package.json');
    (0, _LogUtils.log)('It should look something like this:');
    console.log();
    console.log('  {');
    console.log('    ...');
    console.log('    "react-project": {');
    console.log('      "server": "modules/server.js",');
    console.log('      "client": "modules/client.js"');
    console.log('    }');
    console.log('  }');
    console.log();
    process.exit();
  }
}

function logCantReadPackageJSON() {
  logError('Can\'t read "package.json", go fix it or maybe run this:');
  (0, _LogUtils.log)();
  (0, _LogUtils.log)('  npm init .');
  (0, _LogUtils.log)();
  process.exit();
}

function logNoPackageJSON() {
  (0, _LogUtils.log)('No "package.json" found, run this:');
  (0, _LogUtils.log)();
  (0, _LogUtils.log)('  npm init .');
  (0, _LogUtils.log)();
  process.exit();
}

function logNoDX() {
  (0, _LogUtils.log)('No "react-project" entry found in package.json');
  (0, _LogUtils.log)('It should look something like this:');
  console.log();
  console.log('  {');
  console.log('    ...');
  console.log('    "react-project": {');
  console.log('      "server": "modules/server.js",');
  console.log('      "client": "modules/client.js",');
  console.log('      "webpack": "modules/webpack.config.js"');
  console.log('    }');
  console.log('  }');
  console.log();
  process.exit();
}