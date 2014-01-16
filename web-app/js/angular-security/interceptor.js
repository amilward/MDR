angular.module('security.interceptor', ['security.retryQueue'])

// This http interceptor listens for authentication failures
    .factory('securityInterceptor', ['$injector', 'securityRetryQueue', function ($injector, queue) {
        return function (promise) {
            // Intercept failed requests
            return promise.then(null, function (originalResponse) {
                if (originalResponse.status === 401) {
                    // The request bounced because it was not authorized - add a new request to the retry queue
                    promise = queue.pushRetryFn('unauthorized-server', function retryRequest() {
                        // We must use $injector to get the $http service to prevent circular dependency
                        return $injector.get('$http')(originalResponse.config);
                    });
                }
                return promise;
            });
        };
    }])

// We have to add the interceptor to the queue as a string because the interceptor depends upon service instances that are not available in the config block.
    .config(['$httpProvider', function ($httpProvider) {
        $httpProvider.responseInterceptors.push('securityInterceptor');
    }])
//
//// register the interceptor as a service
//.factory('myHttpInterceptor', function($q) {
//    return {
//        // optional method
//        'request': function(config) {
//            // do something on success
//            return config || $q.when(config);
//        },
//
//        // optional method
//        'requestError': function(rejection) {
//            // do something on error
//            if (canRecover(rejection)) {
//                return responseOrNewPromise
//            }
//            return $q.reject(rejection);
//        },
//
//
//
//        // optional method
//        'response': function(response) {
//            // do something on success
//            return response || $q.when(response);
//        },
//
//        // optional method
//        'responseError': function(rejection) {
//            // do something on error
//            if (canRecover(rejection)) {
//                return responseOrNewPromise
//            }
//            return $q.reject(rejection);
//        }
//    };
//})
//
//.config(['$httpProvider', function($httpProvider) {
//    $httpProvider.interceptors.push('myHttpInterceptor');
//}]);