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
            goToWork: function (driverId) {
                return $q(function (resolve, reject) {
                    $http.put('/driver-command/driver/' + driverId + '/status', {driverStatus: 'EMPTY'})
                        .success(function (data) {
                            resolve(data);
                        })
                        .error(function (data, status) {
                            console.log(status);
                        });
                });
            },




//            loadBankTransfers: function (bankAccountId) {
//                return $q(function (resolve, reject) {
//                    $stomp.subscribe('/app/bank-accounts/' + bankAccountId + '/bank-transfers', function (data) {
//                        resolve(data);
//                    });
//                });
//            },
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