'use strict';

angular.module('axonBank')
    .controller('BankAccountsCtrl', function ($scope, $uibModal, BankAccountService) {

        $scope.currentDriver = {};
        $scope.currentOrder = {};
        $scope.orderList = [];

        function updateOrderList(orderList) {
            $scope.orderList = orderList;
        };
        function updateDriverList(driverList) {
            $scope.driverList = driverList;

            if (typeof $scope.currentDriver.driverId != 'undefined') {
                $scope.currentDriver = $scope.driverList.find(
                    function(element) { return element.driverId == $scope.currentDriver.driverId; }
                );
//                if($scope.currentDriver.assignedOrderId != 'null') {
//                    $scope.currentOrder = $scope.orderList.find(
//                        function(element) { return element.orderId == $scope.currentDriver.assignedOrderId; }
//                    );
//                }
                updateCurrentOrder();
            }
        };
        function updateCurrentOrder() {
            if($scope.currentDriver.assignedOrderId != 'null') {
                $scope.currentOrder = $scope.orderList.find(
                    function(element) { return element.orderId == $scope.currentDriver.assignedOrderId; }
                );
            }
        };

        BankAccountService.connect()
            .then(function () {
                BankAccountService.loadOrderList().then(updateOrderList);
                BankAccountService.subscribeToOrderListUpdates()
                    .then(function () {
                        // do nothing
                    }, function () {
                        // do nothing
                    }, updateOrderList);

                BankAccountService.loadDriverList().then(updateDriverList);
                BankAccountService.subscribeToDriverListUpdates()
                    .then(function () {
                        // do nothing
                    }, function () {
                        // do nothing
                    }, updateDriverList);

            });

        $scope.createOrder = function () {
            $uibModal.open({
                controller: 'CreateOrderModalCtrl',
                templateUrl: '/app/modals/createOrderModal.html',
                ariaLabelledBy: 'modal-title',
                ariaDescribedBy: 'modal-body'
            });
        };

        $scope.deleteOrder = function (orderId) {
            BankAccountService.deleteOrder(orderId);
        }

        $scope.selectDriver = function () {
            var modalInstance = $uibModal.open({
                controller: 'SelectDriverModalCtrl',
                templateUrl: '/app/modals/selectDriverModal.html',
                ariaLabelledBy: 'modal-title',
                ariaDescribedBy: 'modal-body',
                resolve: {
                    driverList: function () {
                        return $scope.driverList;
                    },
                    currentDriver: function() {
                        return $scope.currentDriver;
                    }
                }
            });
            modalInstance.result.then(function (selectedDriver) {
                $scope.currentDriver = angular.fromJson(selectedDriver);
                updateCurrentOrder();
            }, function () {
//                console.log("No data");
            });
        };
        $scope.goToWork = function () {
            var statusObj = {};
            if ($scope.currentDriver.driverStatus == 'ON_VACATION') {
                statusObj.driverStatus = "EMPTY";
                BankAccountService.setDriverStatus($scope.currentDriver.driverId, statusObj);
            } else if ($scope.currentDriver.driverStatus == 'EMPTY') {
                statusObj.driverStatus = "ON_VACATION";
                BankAccountService.setDriverStatus($scope.currentDriver.driverId, statusObj);
            }
        }
        $scope.pickupOrder = function (order) {
            var result = BankAccountService.pickupOrder($scope.currentDriver.driverId, order.orderId);
            console.log("result: ");
            console.log(result);
            $scope.currentOrder = order;
            console.log('currentOrder');
            console.log($scope.currentOrder);
        }
        $scope.finishOrder = function (order) {
            var result = BankAccountService.finishOrder($scope.currentDriver.driverId, order.orderId);
            console.log("result: ");
            console.log(result);
            $scope.currentOrder = null;
            console.log('currentOrder');
            console.log($scope.currentOrder);
        }

    })
    .controller('CreateOrderModalCtrl', function ($uibModalInstance, $scope, BankAccountService) {
        $scope.order = {};

        $scope.cancel = function () {
            $uibModalInstance.dismiss();
        };
        $scope.submit = function () {
            BankAccountService.createOrder($scope.order);
            $uibModalInstance.close();
        };
    })
    .controller('SelectDriverModalCtrl', function ($uibModalInstance, $scope, BankAccountService, driverList, currentDriver) {
        $scope.driverList = driverList;
        $scope.selectedDriver = {};

        $scope.cancel = function () {
            $uibModalInstance.dismiss();
        };
        $scope.submit = function () {
            $uibModalInstance.close($scope.selectedDriver);
        };
    });

//    .controller('WithdrawMoneyModalCtrl', function ($uibModalInstance, $scope, BankAccountService, bankAccountId) {
//        $scope.withdrawal = {
//            bankAccountId: bankAccountId
//        };
//
//        $scope.cancel = function () {
//            $uibModalInstance.dismiss();
//        };
//        $scope.submit = function () {
//            BankAccountService.withdraw($scope.withdrawal);
//            $uibModalInstance.close();
//        };
//    })
//    .controller('TransferMoneyModalCtrl',
//        function ($uibModalInstance, $scope, BankAccountService, bankAccountId, bankAccounts) {
//            $scope.bankAccounts = bankAccounts;
//            $scope.bankTransfer = {
//                sourceBankAccountId: bankAccountId
//            };
//
//            $scope.cancel = function () {
//                $uibModalInstance.dismiss();
//            };
//            $scope.submit = function () {
//                BankAccountService.transfer($scope.bankTransfer);
//                $uibModalInstance.close();
//            };
//        })
//    .controller('BankTransfersModalCtrl',
//        function ($uibModalInstance, $scope, BankAccountService, bankAccountId, bankTransfers) {
//            $scope.bankAccountId = bankAccountId;
//            $scope.bankTransfers = bankTransfers;
//
//            $scope.close = function () {
//                $uibModalInstance.close();
//            };
//        });


//        $scope.deposit = function (id) {
//            $uibModal.open({
//                controller: 'DepositMoneyModalCtrl',
//                templateUrl: '/app/modals/depositMoneyModal.html',
//                ariaLabelledBy: 'modal-title',
//                ariaDescribedBy: 'modal-body',
//                resolve: {
//                    bankAccountId: function () {
//                        return id;
//                    }
//                }
//            });
//        };
//
