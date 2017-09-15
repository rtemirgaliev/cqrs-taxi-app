'use strict';

angular.module('axonBank')
    .controller('BankAccountsCtrl', function ($scope, $uibModal, BankAccountService) {

        $scope.currentDriver = {};

        function updateOrderList(orderList) {
            $scope.orderList = orderList;
        };
        function updateDriverList(driverList) {
            $scope.driverList = driverList;
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

                BankAccountService.subscribeToDriverListUpdates()
                    .then(function () {
                        // do nothing
                    }, function () {
                        // do nothing
                    }, updateDriverList);

                BankAccountService.loadDriverList().then(updateDriverList);
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
//                $scope.currentDriver = angular.fromJson(selectedDriver);
                var selectedDriverObj = angular.fromJson(selectedDriver);
                $scope.currentDriver = $scope.driverList.findIndex( function(element) { return element.driverId == selectedDriverObj.driverId } );
                console.log("result:" + $scope.currentDriver);
            }, function () {
                console.log("Error in modalInstance.result" + selectedDriver);
            });
        };
        $scope.goToWork = function () {
            BankAccountService.goToWork();
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
