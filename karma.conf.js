// an example karma.conf.js
module.exports = function(config) {
	config.set({
		basePath: '.',
		frameworks: ['jasmine'],
		browsers: ['Chrome', 'Firefox'],
		//browsers: ['Chrome', 'Firefox', 'Safari'],
		reporters: ['progress', 'junit', 'coverage'],
		singleRun: false,
        autoWatch : true,

		coverageReporter: {
			type: 'lcovonly',
			dir: 'target/reports/js/coverage/'
		},

		junitReporter: {
			outputFile: 'target/reports/js/karma-test-results.xml',
            suite: 'unit'
		},
		
		files: [
			// MOST OF THE FOLLOWING SHOULD BE REMOVED (or at least fixed and put in lib/)
            'grails-app/assets/bower_components/jquery/jquery.js',
            'grails-app/assets/bower_components/angular/angular.js',
            'grails-app/assets/bower_components/angular-resource/angular-resource.js',

            'test/frontend/lib/angular/angular-mocks.js',
            //'test/frontend/lib/angular/angular-scenario.js',
            'test/frontend/**/*.coffee',
            'test/frontend/unit**/*.js'
		],
		exclude: [
		],

		plugins: [
			'karma-coverage',
			'karma-jasmine',
			'karma-chrome-launcher',
			'karma-firefox-launcher',
			'karma-safari-launcher',
			'karma-junit-reporter',
            'karma-coffee-preprocessor'
		],

        preprocessors: {
            '**/*.coffee': ['coffee']
        },

        coffeePreprocessor: {
            // options passed to the coffee compiler
            options: {
                bare: true,
                sourceMap: false
            },
            // transforming the filenames
            transformPath: function(path) {
                return path.replace(/\.js$/, '.coffee');
            }
        }
	});
};
