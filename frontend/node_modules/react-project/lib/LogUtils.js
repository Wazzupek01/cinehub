'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.log = log;
exports.logError = logError;
exports.logPrompt = logPrompt;
exports.logTask = logTask;
exports.promptApproval = promptApproval;

var _prompt = require('prompt');

var _prompt2 = _interopRequireDefault(_prompt);

var _cliColor = require('cli-color');

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _toConsumableArray(arr) { if (Array.isArray(arr)) { for (var i = 0, arr2 = Array(arr.length); i < arr.length; i++) { arr2[i] = arr[i]; } return arr2; } else { return Array.from(arr); } } /*eslint no-console: 0*/


function logWithColor(color, msgs) {
  var _console;

  (_console = console).log.apply(_console, _toConsumableArray([color('[react-project]')].concat(msgs)));
}

function log() {
  var _console2;

  for (var _len = arguments.length, msgs = Array(_len), _key = 0; _key < _len; _key++) {
    msgs[_key] = arguments[_key];
  }

  (_console2 = console).log.apply(_console2, _toConsumableArray(['[react-project]'].concat(msgs)));
}

function logError() {
  for (var _len2 = arguments.length, msgs = Array(_len2), _key2 = 0; _key2 < _len2; _key2++) {
    msgs[_key2] = arguments[_key2];
  }

  logWithColor(_cliColor.red, msgs);
}

function logPrompt() {
  for (var _len3 = arguments.length, msgs = Array(_len3), _key3 = 0; _key3 < _len3; _key3++) {
    msgs[_key3] = arguments[_key3];
  }

  logWithColor(_cliColor.yellow, msgs);
}

function logTask() {
  for (var _len4 = arguments.length, msgs = Array(_len4), _key4 = 0; _key4 < _len4; _key4++) {
    msgs[_key4] = arguments[_key4];
  }

  logWithColor(_cliColor.green, msgs);
}

function promptApproval(msg, cb) {
  if (process.env.NODE_ENV === 'test' || process.env.NODE_ENV === 'production') {
    logError('Wanted to prompt approval but skipping because we are in production or test');
    log('Prompt message was: ', msg);
    cb();
  } else {
    _prompt2.default.start();
    var property = {
      name: 'yesno',
      message: msg + ' (y|n)',
      validator: /y|n/,
      warning: 'Must respond "y" for yes or "n" for no',
      default: 'n'
    };
    _prompt2.default.get(property, function (err, result) {
      if (result.yesno === 'y') {
        cb && cb();
      } else {
        logError('FINE!');
        process.exit();
      }
    });
  }
}