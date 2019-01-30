window.onclick = function(ev){
    if( ev.target.nodeName !== 'A' ){
        PF('ratingOverlay').hide();
    }
    refreshRatings();
};