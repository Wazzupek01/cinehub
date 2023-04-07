'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.KarmaConf = KarmaConf;

var _webpack = require('webpack');

var _webpack2 = _interopRequireDefault(_webpack);

var _webpackShared = require('./webpack.shared.config');

var SHARED = _interopRequireWildcard(_webpackShared);

function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } else { var newObj = {}; if (obj != null) { for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) newObj[key] = obj[key]; } } newObj.default = obj; return newObj; } }

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

/*eslint no-console: 0*/
function KarmaConf(config) {
  config.set({
    browsers: ['Chrome'],
    frameworks: ['mocha'],
    reporters: ['mocha'],

    files: ['tests.webpack.js'],

    preprocessors: {
      'tests.webpack.js': ['webpack', 'sourcemap']
    },

    webpack: {
      devtool: 'inline-source-map',
      module: {
        loaders: [{ test: SHARED.JS_REGEX,
          loader: 'babel-loader'
        }, { test: SHARED.CSS_REGEX,
          loader: 'style-loader!css-loader?' + SHARED.CSS_LOADER_QUERY + '!postcss-loader'
        }, { test: SHARED.JSON_REGEX,
          loader: 'json-loader'
        }, { test: SHARED.FONT_REGEX,
          loader: 'url-loader?limit=10000'
        }, { test: SHARED.IMAGE_REGEX,
          loader: 'url-loader?limit=10000'
        }, { test: /modules\/api\//,
          loader: 'null-loader'
        }]
      },
      plugins: [new _webpack2.default.DefinePlugin({
        'process.env.NODE_ENV': JSON.stringify('test'),
        'process.env.FAIL_ON_WARNINGS': JSON.stringify(process.env.FAIL_ON_WARNINGS || false)
      })]
    },

    webpackServer: {
      noInfo: true
    }

  });
}