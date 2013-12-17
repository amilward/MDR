// an example karma.conf.js
module.exports = function(config) {
	config.set({
		basePath: '',
		frameworks: ['jasmine'],
		browsers: ['Chrome', 'Firefox'],
		//browsers: ['Chrome', 'Firefox', 'Safari'],
		reporters: ['progress', 'junit', 'coverage'],
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
		
		files: [
			// MOST OF THE FOLLOWING SHOULD BE REMOVED (or at least fixed and put in lib/)
			'web-app/js/vendor/jquery/*.js',
			'web-app/js/vendor/jquery-ui.1.10.2.js',
			'web-app/js/datatable/jquery.dataTables.min.js',
			'web-app/js/datatable/jquery.dataTables.fnSetFilteringDelay.js',
			'web-app/js/vendor/bootstrap/*.js',
			'web-app/js/lib/**/*.js',
			'web-app/js/vendor/**/*.js',
			'web-app/js/*/*.js',
			'test/js/**/*.js'
		],
		exclude: [
			'web-app/js/vendor/bootstrap-editable.js',
			'web-app/js/vendor/require/require.js',
			'web-app/js/pathways/main.js',
			'web-app/js/pathways/AppViewModel.js',
		],

		plugins: [
			'karma-coverage',
			'karma-jasmine',
			'karma-chrome-launcher',
			'karma-firefox-launcher',
			'karma-safari-launcher',
			'karma-junit-reporter',
		]
	});
};
