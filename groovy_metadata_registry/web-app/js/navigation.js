//create unbound boolean - add check to ensure that flip behaviour hasn't been unbound before binding (animation and timing issue)
var unbound = false;
var firstOpen = true;
var originalTitle=document.title
var lastPage;
var loadingNewPage = false;



/* 
var pageLoaded = $.Deferred();
 
/* add handlers to be called when dfd is resolved 
pageLoaded.done(function(n) {
  alert('pageLoaded' + n)
});

.done() can take any number of functions or arrays of functions
.done( [fn1, fn2], fn3, [fn2, fn1] )
/* we can chain done methods, too */

 
/* resolve the Deferred object when the button is clicked 
$("button").on("click", function() {
  
});*/





function setupNavigation() {

	/*this function sets up the navigation using hastags so we can use ajax page loads and animations
	 * but can still use the browser back button etc.
	 */

	//detect hash change in browser
	
	if ("onhashchange" in window) {// cool browser
		$(window).on('hashchange', hashChange).trigger('hashchange')
	} else {// lame browser
		var lastHash = ''
		setInterval(function() {
			if (lastHash != location.hash)
				hashChange()
			lastHash = location.hash
		}, 100)
	}
}

//behaviour when hash changed

	function hashChange() {

		//get hash i.e. page 
		var page = '';
		page = location.hash.slice(1)
		var color = '';
		var direction = 'backward';

			switch(page) {
				case 'home':
					unbound = false;
					direction = 'forward';
					break;
				case 'we_are':
					//execute code block 2
					color = '#DE4833'
					break;

				case 'web':
					//execute code block 2
					color = '#FFCF06'
					break;

				case 'design':
					//execute code block 2
					color = '#F19128'
					break;

				case 'mobile':
					//execute code block 2
					color = '#A11F7F'
					break;

				case 'weve_done':
					//execute code block 2
					color = '#7C287C'
					break;

				case 'we_do':
					//execute code block 2
					color = '#D81F2E'
					break;
					
				case 'services':
							color='#FCCD06'; break;

				case 'search':
					color='#EF4E37'; 
					break;
					
				case 'data':
					
					color='#DB1F2F'; 
					break;
					
				case 'contact':
					color = '#AE1C6A'; 
					break;

				default:
				//code to be executed if n is different from case 1 and 2
				unbound = false;
				direction = 'forward';
				page = 'home';
				break;
			}

			loadNewPage(color, page, direction);
	}


function loadNewPage(color, page, direction){
		if(page=='home'){
			
			loadDashboard(page);
			
		}else{	
			loadingNewPage = true;		
			if(firstOpen){
				
				//load dashboard background
				
				$('#home').load(appContext  + "/home.html", function() {
						unbindFlipBoxes();
						changeDashboardColor(page, direction, color, loadPage);
				});
				
				firstOpen = false;
				
			}else{
	
				/////////////////////////////////////////////////////////////////////////////////////////////////////////////
			///////////////REMOVE mouseover HANDLERS for flip - this is to stop it interfering with page change animation/////////////////
	
				unbindFlipBoxes();
				
				//change dashboard color
				
				changeDashboardColor(page, direction, color, loadPage);
			}

	}
	
}



/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////generic functions used by the home page
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////


function changeDashboardColor(page, direction, color, callback){
	
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//set up dashboard change color

			var delay_disappear = 0;
					
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//animate the dashboard disappearing
			var boxes = '';
					
			if(direction=='backward'){boxes = $('.big, .small').get().reverse();}else{ boxes = $('.big, .small').get()}
					
					
					
			$(boxes).each(function() {		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//iterate through dashboard boxes changing their color and removing background text
				$(this).find('> :first-child').css('opacity', '0');
							
				$(this).delay(delay_disappear).animate({
					backgroundColor : color,
					opacity: 1
				}, 200);
				delay_disappear += 200;
			}).promise().done(function() {

				
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//load page to sit on background and slide into place
				
				if(callback!='' && callback!=null){
					callback(page)
					};

			});
	
	
}


function loadDashboard(page) {
	
	//unload any content in the content block

		if(firstOpen){
			//load home page for the first time
			$('#home').load(appContext + "/" + page + ".html", function() {
	
				/////////////////////////////////////////////////////////////////////////////////////////////////////////////
				/////once dashboard loaded animate boxes/menu appearance - appear one by one//////////
	
				openDashboard();

			});
			
			firstOpen = false;
			
		}else{	
			
			
			
			unLoadPage(lastPage, openDashboard);
			
		}

	}
	
function loadPage(page) {

	location.hash=page;
	lastPage = location.hash;
	
	$('#content').load(appContext  + "/" +  page + '.html', function() {

		setTimeout(function(){
			$('#'+page).show('slide', {
			direction : 'left'
			}, 1000);
			loadingNewPage = false;		
		},400)
	})
	
	
}



function unLoadPage(page, callbackfunction){
	
	
	
	var parent_div = $(page).parent();
	parent_div.css("height", parent_div.height()+"px");
	parent_div.css("width", parent_div.height()+"px");
	setTimeout(function(){
		$(page).hide('slide', {
		direction : 'left' }, 1500).promise().done(function(){
			parent_div.attr('style', '');
			callbackfunction();
			
		});	
		},400)
	
}








function openDashboard(){
	
	var delay = 0;
	
				$('.big, .small').each(function() {
					
					var box = $(this).attr('data-page');
					var backgroundcolor = '';
					
					switch(box){
											
						case 'we_are':
							backgroundcolor = '#DE4833';
							break;
						
						case 'weve_done':
							backgroundcolor='#7C287C';
							break;
							
						case 'we_do':
							backgroundcolor='#D81F2E'; 
							break;
						
						case 'web':
							backgroundcolor='#FFCF06'; 
							break;
						case 'mobile':	
							backgroundcolor='#A11F7F'; 
							break;
						
						case 'design':
							backgroundcolor='#F19128'; 
							break;
							
						case 'data':
							
							backgroundcolor='#DB1F2F'; 
							break;
						
						case 'services':
							backgroundcolor='#FCCD06'; break;
						case 'search':
							backgroundcolor='#EF4E37'; break;
						
						case 'contact':
							backgroundcolor = '#AE1C6A'; break;
						
						default:
						
							backgroundcolor='#AE1C6A'; break;			
						
					}
					
					$(this).children('p').attr('style','opacity: 0');
					
					$(this).delay(delay).animate({
						backgroundColor: backgroundcolor,
						opacity : 1
					}, 200);
					
					
					$(this).children('p').delay(delay).animate({
						opacity : 1
					}, 200);
					
					
					delay += 200;
										
					
				}).promise().done(function() {
					
					//if other page isn't loading
					if(!loadingNewPage){
						$(this).attr('style','opacity:1');
					}

					/////////////////////////////////////////////////////////////////////////////////////////////////////////////
					/////////////////////once dashboard boxes all appear bind flip behaviour to search and web/////////////////////////////////////////////
	
					bindFlipBoxes();
	
				});
	
	
					/////////////////////////////////////////////////////////////////////////////////////////////////////////////
				/////////////////////////////////////// bind dashboard behaviour to buttons ///////////////////////////////////////
	
				addDashboardBehaviour();
}




/////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////// add dashboard behaviour///////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////

function addDashboardBehaviour(){

		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////// add generic click handler for boxes/menu and behaviour ///////////////////////////////////////

		$('.big, .small').click(function() {


			var page = $(this).attr('data-page');
			location.hash=page;

		});

}






/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//unbind flip boxes on home page
/////////////////////////////////////////////////////////////////////////////////////////////////////////////

function unbindFlipBoxes(){
			
			$('.web').stop().unbind('mouseenter');
			$('.search').stop().unbind('mouseenter');
			unbound = true;

}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//bind flip boxes on home page
/////////////////////////////////////////////////////////////////////////////////////////////////////////////

function bindFlipBoxes() {

		if (!unbound) {
				$(".web").bind('mouseenter', function(e)/* IN */
				{
					if (!unbound) {
						$(this).flip({
							direction : 'lr',
							color : '#FFCF06',
							onBefore : function() {
	
								$('.web').addClass('notransition');
								// to remove transition
							},
							onAnimation : function() {
							},
							onEnd : function() {
								$('.web').removeClass('notransition');
								$('.web').attr('style', 'opacity:1');
	
							}
						});
					}
					unbound = true;
				});


				$(".web").bind('mouseleave', function(e){
					unbound = false;
				})

				$(".search").bind('mouseenter', function(e)/* IN */
				{
					if (!unbound) {
					$(this).flip({
						direction : 'lr',
						color : '#EF4E37',
						onBefore : function() {

							$('.search').addClass('notransition');
							// to remove transition
						},
						onAnimation : function() {
						},
						onEnd : function() {
							$('.search').removeClass('notransition');
							$('.search').attr('style', 'opacity:1');

						}
					});
					}
					unbound = true;
				});
				
				$(".search").bind('mouseleave', function(e){
					unbound = false;
				})
				
			}

}





