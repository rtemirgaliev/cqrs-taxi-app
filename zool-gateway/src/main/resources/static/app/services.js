'use strict';

angular.module('axonBank')
    .service('BankAccountService', function ($stomp, $q, $http) {

        var isConnected = false;

        return {
            connect: function () {
                return $q(function (resolve, reject) {
                    if (!isConnected) {
                        $stomp.connect('/websocket')
                            .then(function (frame) {
                                isConnected = true;
                                resolve();
                            })
                            .catch(function (reason) {
                                reject(reason);
                            });
                    }
                    else {
                        resolve();
                    }
                });
            },
            loadOrderList: function () {
                return $q(function (resolve, reject) {
                    $http.get('/order-query/order')
                        .success(function (data) {
                            resolve(data);
                        })
                        .error(function (data, status) {
                            console.log(status);
                        });
                });
            },
            createOrder: function (order) {
                return $q(function (resolve, reject) {
                    $http.post('/order-command/order', order, {transformResponse: function(resp) {return resp;} }) //{headers: {'Content-Type': 'application/json'}}
                        .success(function (data) {
                            resolve(data);
                        })
                        .error(function (data, status) {
                            console.log(data);
                        });
                });
            },
            deleteOrder: function (orderId) {
                return $q(function (resolve, reject) {
                    $http.delete('/order-command/order/'+orderId)
                        .success(function (data) {
                            resolve(data);
                        })
                        .error(function (data, status) {
                            console.log(data);
                        });
                });
            },
            loadDriverList: function () {
                return $q(function (resolve, reject) {
                    $http.get('/driver-query/driver')
                        .success(function (data) {
                            resolve(data);
                        })
                        .error(function (data, status) {
                            console.log(status);
                        });
                });
            },
            setDriverStatus: function (driverId, statusObj) {
                return $q(function (resolve, reject) {
                    $http.put('/driver-command/driver/' + driverId + '/status', statusObj)
                        .success(function (data) {
                            resolve(data);
                        })
                        .error(function (data, status) {
                            console.log(status);
                        });
                });
            },
            pickupOrder: function (driverId, orderId) {
                 console.log('pickup function..');
                 var orderRequest = {};
                 orderRequest.orderId = orderId;
                 return $q(function (resolve, reject) {
                    $http.post('/driver-command/driver/' + driverId + '/order', orderRequest,
                            {transformResponse: function(resp) {return resp;} })
                        .success(function (data) {
                            resolve(orderId);
                        })
                        .error(function (data, status) {
                            console.log(status);
                        });
                 });
            },
            finishOrder: function (driverId, orderId) {
                 console.log('finish function..');
                 var orderRequest = {};
                 orderRequest.orderId = orderId;
                 return $q(function (resolve, reject) {
                    $http.post('/driver-command/driver/' + driverId + '/order/' + orderId + '/finish', orderRequest,
                            {transformResponse: function(resp) {return resp;} })
                        .success(function (data) {
                            resolve(orderId);
                        })
                        .error(function (data, status) {
                            console.log(status);
                        });
                 });
            },

            subscribeToOrderListUpdates: function () {
                var deferred = $q.defer();
                $stomp.subscribe('/topic/order-list.updates', function (data) {
                    deferred.notify(data);
                });
                return deferred.promise;
            },
            subscribeToDriverListUpdates: function () {
                var deferred = $q.defer();
                $stomp.subscribe('/topic/driver-list.updates', function (data) {
                    deferred.notify(data);
                });
                return deferred.promise;
            },



//            createBankAccount: function (data) {
//                $stomp.send('/app/bank-accounts/create', data);
//            },
//            deposit: function (data) {
//                $stomp.send('/app/bank-accounts/deposit', data);
//            },
//            withdraw: function (data) {
//                $stomp.send('/app/bank-accounts/withdraw', data);
//            },
//            transfer: function (data) {
//                $stomp.send('/app/bank-transfers/create', data);
//            }
        };
    });