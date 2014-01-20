describe "Toolbar nav-link directive", ->
  scope = undefined
  element = undefined
  $location = undefined

  beforeEach module "appng.toolbar"

  beforeEach(inject(($compile, $rootScope, _$location_) ->
    $location = _$location_
    linkingFn = $compile '<li id="nav-li"><a id="nav-a" href="#/list/users" route-link>User List</a></li>'
    scope = $rootScope
    element = linkingFn scope
  ))

  it "the element is not active by default", ->
    expect(element.hasClass("active")).toBe(false)

  it "the element is active if we navigate on the linked page", ->
    $location.path("/list/users")
    scope.$broadcast("$routeChangeSuccess")
    expect(element.hasClass("active")).toBe(true)

  it "the element is no longer active if we navigate on the different page", ->
    $location.path("/list/users/whatever")
    scope.$broadcast("$routeChangeSuccess")
    expect(element.hasClass("active")).toBe(false)