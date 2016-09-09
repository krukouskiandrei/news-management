function createPageButtons(countNews, pageNum, path){
	var parentTag = document.getElementById('page-buttons');
  	var countNewsOnPage = 3;
  	var countPage = countNews / countNewsOnPage;
  	countPage = parseInt(countPage);
  	if(countPage * countNewsOnPage != countNews){
  		countPage++;
  	}
    var currentNumPage = pageNum;
    var allNumPage = countPage;
    var countButtonOnPage = 8;
    $(parentTag).empty();
    if(currentNumPage < 5){
    	var left = 1;
    	var right;
    	if(allNumPage <= countButtonOnPage){
    		right = allNumPage;
    	}else{
    		right = countButtonOnPage;
    	}
    	var elementLinkOnPage;
    	for(var i = left; i <= right; i++){
    		elementLinkOnPage = document.createElement('a');
    		elementLinkOnPage.setAttribute("class", "buttonPage");
    		elementLinkOnPage.setAttribute("href", path + "/news/page/" + i);
    		elementLinkOnPage.setAttribute("countPage", allNumPage);
    		$(elementLinkOnPage).append(i);
    		if(i == currentNumPage){
    			$(elementLinkOnPage).css("background-color", "#A69B9B");
    		}
    		parentTag.appendChild(elementLinkOnPage);
    	}
    	return;     
    }
    if(allNumPage - currentNumPage > 3){
    	var left = currentNumPage - 3;
    	var right = left + countButtonOnPage - 1;
    	var elementLinkOnPage;
    	for(var i = left; i <= right; i++){
    		elementLinkOnPage = document.createElement('a');
    		elementLinkOnPage.setAttribute("class", "buttonPage");
    		elementLinkOnPage.setAttribute("href", path + "/news/page/" + i);
    		elementLinkOnPage.setAttribute("countPage", allNumPage);
    		$(elementLinkOnPage).append(i);
    		if(i == currentNumPage){
    			$(elementLinkOnPage).css("background-color", "#A69B9B");
    		}
    		parentTag.appendChild(elementLinkOnPage);
    	}
    }else{
    	var left = allNumPage - countButtonOnPage + 1;
    	var right = allNumPage;
    	var elementLinkOnPage;
    	for(var i = left; i <= right; i++){
    		elementLinkOnPage = document.createElement('a');
    		elementLinkOnPage.setAttribute("class", "buttonPage");
    		elementLinkOnPage.setAttribute("href", path + "/news/page/" + i);
    		elementLinkOnPage.setAttribute("countPage", allNumPage);
    		$(elementLinkOnPage).append(i);
    		if(i == currentNumPage){
    			$(elementLinkOnPage).css("background-color", "#A69B9B");
    		}
    		parentTag.appendChild(elementLinkOnPage);
    	}
    }
};
