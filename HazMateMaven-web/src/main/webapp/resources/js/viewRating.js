window.onclick = function(ev){
    console.log(ev.target.nodeName);
    console.log(ev.target.className);
    if( ev.target.nodeName !== 'A' ){
        PF('ratingOverlay').hide();
    }
};