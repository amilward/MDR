
var tests = [{ 
	name : "True, false",
	constraints : [["true",true],
	               ["false",false]]
},
{
	name : "Not",
	constraints : [["not true", false],
	               ["not false", true]]
},
{
	name : "And",
	constraints : [["true && true", true],
	               ["true && false", false],
	               ["false && true", false],
	               ["false && false", false]]
},
{
	name : "Or",
	constraints : [["true || true", true],
	               ["true || false", true],
	               ["false || true", true],
	               ["false || false", false]]
},
{
	name : "Brackets",
	constraints : [["(true)", true],
	               ["(false)", false],
	               ["(false && true)", false],
	               ["(false || true)", true]]
},
{
	name : "Equals",
	constraints : [["1 = 0", false],
	               ["0 = 1", false],
	               ["1 = 1", true],
	               ["a = 10", true],
	               ["a = 11", false],
	               ["a = b", false],
	               ["a = a", true]]
},
{
	name : "Not Equals",
	constraints : [["1 /= 0", true],
	               ["0 /= 1", true],
	               ["1 /= 1", false],
	               ["a /= 10", false],
	               ["a /= 11", true],
	               ["a /= b", true],
	               ["a /= a", false]]
},
{
	name : "Greater Than",
	constraints : [["1 > 0", true],
	               ["0 > 1", false],
	               ["1 > 1", false],
	               ["a > 10", false],
	               ["a > 9", true],
	               ["a > b", false],
	               ["b > a", true],
	               ["a > a", false]]
},
{
	name : "Greater Than Equals",
	constraints : [["1 >= 0", true],
	               ["0 >= 1", false],
	               ["1 >= 1", true],
	               ["a >= 11", false],
	               ["a >= 10", true],
	               ["a >= b", false],
	               ["b >= a", true],
	               ["a >= a", true]]
},
{
	name : "Less Than",
	constraints : [["1 < 0", false],
	               ["0 < 1", true],
	               ["1 < 1", false],
	               ["a < 10", false],
	               ["a < 9", false],
	               ["a < b", true],
	               ["b < a", false],
	               ["a < a", false]]
},
{
	name : "Less Than Equals",
	constraints : [["1 <= 0", false],
	               ["0 <= 1", true],
	               ["1 <= 1", true],
	               ["a <= 11", true],
	               ["a <= 10", true],
	               ["a <= b", true],
	               ["b <= a", false],
	               ["a <= a", true]]
},
{
	name : "Addition",
	constraints : [["1 + 0 = 1", true],
	               ["0 + 1 = 1", true],
	               ["1 + 0 = 0", false],
	               ["0 + 1 = 0", false],
	               ["1 = 1 + 0", true],
	               ["1 = 0 + 1", true],
	               ["0 = 1 + 0", false],
	               ["0 = 0 + 1", false]]
}
];

var tests2 = [{ 
	name : "Double negation",
	constraints : [["not not true","true"],
	               ["not not false","false"]]
},
{ 
	name : "De Morgan 1",
	constraints : [["not ( true && true)", "not true || not true"],
	               ["not ( true && false)", "not true || not false"],
	               ["not ( false && true)", "not false || not true"],
	               ["not ( false && false)", "not false || not false"],]
},
{ 
	name : "De Morgan 2",
	constraints : [["not ( true || true)", "not true && not true"],
	               ["not ( true || false)", "not true && not false"],
	               ["not ( false || true)", "not false && not true"],
	               ["not ( false || false)", "not false && not false"],]
},
{ 
	name : "Basic arithmetic",
	constraints : [["a + b = b + a", "true"],
	               ["b * a = a * b", "true"],
	               ["a + 0 = a", "true"],
	               ["a * 0 = 0", "true"],
	               ["a * 1 = a", "true"],
	               ["a / a = 1", "true"],
	               ["0 / a = 0", "true"]]
}];

var tests3 = [{ 
	name : "Extension testing: isPrime",
	constraints : [["isPrime(197)",true],
	               ["isPrime(196)",false]]
},
{ 
	name : "Extension testing: isFactorOf",
	constraints : [["isFactorOf(10, 100)",true],
	               ["isFactorOf(11, 100)",false]]
}]

function isPrime(n) {
	if (isNaN(n) || !isFinite(n) || n%1 || n<2) return false; 
	if (n%2==0) return (n==2);
	if (n%3==0) return (n==3);
	var m=Math.sqrt(n);
	for (var i=5;i<=m;i+=6) {
		if (n%i==0)     return false;
		if (n%(i+2)==0) return false;
	}
	return true;
}

function isFactorOf(a,b){
	return b % a == 0;
}

//console.log(isFactorOf(10, 100));
//console.log(isFactorOf(11, 100));
//console.log(constraint.parse("isFactorOf(10,100)"));

describe("Constraint functional testing", function() {
	$.each(tests, function(index, test){
		describe(test.name, function(){
			$.each(test.constraints, function(idx, c){
				it(c[0] + " = " + c[1], function(){
					assert(constraint.parse(c[0]) == c[1]);
				});

			});
		});
	});

});

describe("Further constraint testing", function() {
	$.each(tests2, function(index, test){
		describe(test.name, function(){
			$.each(test.constraints, function(idx, c){
				it(c[0] + " = " + c[1], function(){
					assert(constraint.parse(c[0]) == constraint.parse(c[1]));
				});

			});
		});
	});

});

describe("Extension testing", function() {
	$.each(tests3, function(index, test){
		describe(test.name, function(){
			$.each(test.constraints, function(idx, c){
				it(c[0] + " = " + c[1], function(){
					
					assert(constraint.parse(c[0]) == c[1]);
				});

			});
		});
	});

});


