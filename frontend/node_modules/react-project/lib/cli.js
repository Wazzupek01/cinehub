'use strict';

var _LogUtils = require('./LogUtils');

var _build = require('./build');

var _build2 = _interopRequireDefault(_build);

var _start = require('./start');

var _start2 = _interopRequireDefault(_start);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var action = process.argv[2];

var actions = {
  start: _start2.default,
  build: _build2.default
};

if (actions[action]) {
  (0, _LogUtils.logTask)('[' + action + ']', 'task');
  actions[action](function () {
    (0, _LogUtils.logTask)('[' + action + ']', 'task complete');
  });
} else {
  (0, _LogUtils.logError)(action, 'is not a valid command');
}