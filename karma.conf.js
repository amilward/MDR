// an example karma.conf.js
module.exports = function (config) {
    var configurer = {
        basePath: '',
        frameworks: ['jasmine'],
        // browsers: ['Chrome', 'Firefox', 'Safari'],
        browsers: ['Chrome', 'Firefox'],
        reporters: [
            'progress',
            'junit',
            'coverage',
            'html',
            'osx'
        ],

        singleRun: false,

        //preprocessors: {
        //	'web-app/js/!(lib|vendor)/*.js': 'coverage'
        //},
        coverageReporter: {
            type: 'lcovonly',
            dir: 'reports/js/coverage/'
        },

        junitReporter: {
            outputFile: 'reports/js/karma-test-results.xml'
        },

        // the default configuration
        htmlReporter: {
            outputDir: 'reports/karma_html',
            templatePath: __dirname + '/karma_html_report/jasmine_template.html'
        },

        files: [
            // MOST OF THE FOLLOWING SHOULD BE REMOVED (or at least fixed and put in lib/)
            'web-app/js/vendor/jquery/jquery-2.0.3.js',
//			// 'web-app/js/vendor/jquery/jquery-ui.1.10.2.js',
//			'web-app/js/datatable/jquery.dataTables.min.js',
//			'web-app/js/datatable/jquery.dataTables.fnSetFilteringDelay.js',
//			'web-app/js/vendor/bootstrap/*.js',
            'web-app/bower_components/angular/angular.js',
            'web-app/bower_components/angular-mocks/angular-mocks.js',
//			'web-app/bower_components/angular-scenario/angular-scenario.js',
//            'web-app/bower_components/angular-animate/angular-animate.js',
            'web-app/bower_components/angular-bootstrap/ui-bootstrap.js',
            'web-app/bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
//			'web-app/bower_components/angular-cookies/angular-cookies.js',
//			'web-app/bower_components/angular-http-auth/src/http-auth-interceptor.js',
//			'web-app/bower_components/angular-loader/angular-loader.js',
//			'web-app/bower_components/angular-resource/angular-resource.js',
            'web-app/bower_components/angular-route/angular-route.js',
//			'web-app/bower_components/angular-sanitize/angular-sanitize.js',
//            'web-app/bower_components/angular-touch/angular-touch.js',
            // 'web-app/js/lib/**/*.js',
            // 'web-app/js/vendor/**/*.js',
            // 'web-app/js/**/*.js',
            'web-app/js/appng/**/*.js',
            // 'test/js/**/*.js',
            'test/cs/**/*.coffee'
        ],
        exclude: [
            'web-app/js/vendor/knockout/**/*.js',
            'web-app/js/vendor/bootstrap-editable.js',
            'web-app/js/vendor/require/require.js',
            'web-app/js/pathways/main.js',
            'web-app/js/pathways/AppViewModel.js',
            "web-app/js/forms/formspec.js"
        ],

        plugins: [
            'karma-coverage',
            'karma-jasmine',
            'karma-chrome-launcher',
            'karma-firefox-launcher',
            'karma-safari-launcher',
            'karma-junit-reporter',
            'karma-html-reporter',
            'karma-osx-reporter',
            'karma-coffee-preprocessor'
        ],

        autoWatch: true
    }

    config.set(configurer);
};
