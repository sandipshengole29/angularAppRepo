'use strict';

var app = angular.module('app', ['ui.router', 'ngAnimate', 'ui.bootstrap']);

app.config(function($locationProvider, $urlRouterProvider, $stateProvider) {

	$stateProvider.state('login-page', {
		url : '/login-page',
		templateUrl : 'view-lib/common-pages/login-page.html',
		controller : 'loginPageController'
	});

	$stateProvider.state('landing-page', {
		url : '/landing-page',
		templateUrl : 'view-lib/common-pages/landing-page.html',
		controller : 'landingPageController'
	});

	$stateProvider.state('xmlPocStart-page', {
		url : '/xmlPocStart-page',
		templateUrl : 'view-lib/poc-pages/xmlPocStart-page.html',
		controller : 'xmlPocPageController'
	});

	$stateProvider.state('xmlPocView-page', {
		url : '/xmlPocView-page',
		templateUrl : 'view-lib/poc-pages/xmlPocView-page.html',
		controller : 'xmlPocPageController'
	});
	
	$stateProvider.state('xmlPocEdit-page', {
		url : '/xmlPocEdit-page',
		templateUrl : 'view-lib/poc-pages/xmlPocEdit-page.html',
		controller : 'xmlPocPageController'
	});
	
	$stateProvider.state('dynamicControlView-page', {
		url : '/dynamicControlView-page',
		templateUrl : 'view-lib/dynamic-control/dynamicControlView-page.html',
		controller : 'dynamicControlViewController'
	});
	
	$stateProvider.state('searchAccessRole-page', {
		url : '/searchAccessRole-page',
		templateUrl : 'view-lib/dynamic-control/searchAccessRole-page.html',
		controller : 'xmlFieldMappingController'
	});
	
	$stateProvider.state('xmlMappingView-page', {
		url : '/xmlMappingView-page',
		templateUrl : 'view-lib/xmlFieldMapping-pages/xmlMappingView-page.html',
		controller : 'xmlFieldMappingController'
	});
	
	$stateProvider.state('showAndHideView-page', {
		url : '/showAndHideView-page',
		templateUrl : 'view-lib/showAndHide/showAndHideView-page.html',
		controller : 'showAndHideDemoController'
	});
	
	$stateProvider.state('showAndHideEdit-page', {
		url : '/showAndHideEdit-page',
		templateUrl : 'view-lib/showAndHide/showAndHideEdit-page.html',
		controller : 'showAndHideDemoController'
	});

	$urlRouterProvider.otherwise('/login-page');
	$locationProvider.html5Mode(false);
});

app.run(function($rootScope, $location, $state, $window) {

	window.onbeforeunload = function(e) {
		$rootScope.showEditpage = false;
		var bodyDiv = angular.element("#bodyDiv");
		console.log("bodyDiv: " + angular.toJson(bodyDiv));
		bodyDiv.attr("style","width: 82%; margin-left: 3%;");
		
		$state.go("login-page");
	}

});