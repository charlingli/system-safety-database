/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function buildVis() {
    var DLGx = $('#hazardsForm\\:bowtieDialog').position()['left'];
    var DLGy = $('#hazardsForm\\:bowtieDialog').position()['top'];
    
    var SVGx = $('#graphic').position()['left'];
    var SVGy = $('#graphic').position()['top'];
    
    var numCA = $('.visCanvas .left').children().length;
    var numCQ = $('.visCanvas .right').children().length;
    var numPC = $('.visCanvas .mid-left').children().length;
    var numMC = $('.visCanvas .mid-right').children().length;
    
    var wVis = $('#graphic').width();
    var hVis = $('#graphic').height();
    
    var elemHD = $('.visCanvas .mid div');
    var HDl = elemHD.position()['left'];
    var HDr = elemHD.position()['left'] + elemHD.width();
    var HDy = elemHD.position()['top'] + (elemHD.height() / 2);
    
    var elemRF =  elemHD.find('.frequency');
    if (elemRF.html()[1] === "0") {
        elemRF.css('background', '#F2FDFE');
        elemRF.parent().css('border', '1px solid #01D1EE');
    } else if (elemRF.html()[1] === "1") {
        elemRF.css('background', '#F2FDFE');
        elemRF.parent().css('border', '1px solid #01D1EE');
    } else if (elemRF.html()[1] === "2") {
        elemRF.css('background', '#96E9F5');
        elemRF.parent().css('border', '1px solid #2793A2');
    } else if (elemRF.html()[1] === "3") {
        elemRF.css('background', '#56DEF1');
        elemRF.parent().css('border', '1px solid #03899C');
    } else if (elemRF.html()[1] === "4") {
        elemRF.css('background', '#1BD7F1');
        elemRF.parent().css('border', '1px solid #026A79');
    } else if (elemRF.html()[1] === "5") {
        elemRF.css('background', '#01D1EE');
        elemRF.parent().css('border', '1px solid #01535F');
    } else if (elemRF.html()[1] === "6") {
        elemRF.css('background', '#98D5DD');
        elemRF.parent().css('border', '1px solid #01535F');
    }
    
    var elemRS =  elemHD.find('.severity');
    if (elemRS.html()[1] === "0") {
        elemRS.css('background', '#F2FDFE');
        elemRS.parent().css('border', '1px solid #01D1EE');
    } else if (elemRS.html()[1] === "1") {
        elemRS.css('background', '#F2FDFE');
        elemRS.parent().css('border', '1px solid #01D1EE');
    } else if (elemRS.html()[1] === "2") {
        elemRS.css('background', '#96E9F5');
        elemRS.parent().css('border', '1px solid #2793A2');
    } else if (elemRS.html()[1] === "3") {
        elemRS.css('background', '#56DEF1');
        elemRS.parent().css('border', '1px solid #03899C');
    } else if (elemRS.html()[1] === "4") {
        elemRS.css('background', '#1BD7F1');
        elemRS.parent().css('border', '1px solid #026A79');
    } else if (elemRS.html()[1] === "5") {
        elemRS.css('background', '#98D5DD');
        elemRS.parent().css('border', '1px solid #01535F');
    } else if (elemRS.html()[1] === "6") {
        elemRS.css('background', '#98D5DD');
        elemRS.parent().css('border', '1px solid #01535F');
    }
    
    var CAx;
    var CQx;
        
    var draw = SVG('graphic').size(wVis, hVis);
    for (var itr = 1; itr <= numCA; itr += 1) {
        var elemCA = $('.visCanvas .left :nth-child(' + (itr) + ')');
        CAx = elemCA.position()['left'] + elemCA.width();
        var CAy = elemCA.position()['top'] + (elemCA.height() / 2);
        var line = draw.path('M ' + (CAx - SVGx + 2) + ' ' + (CAy - SVGy) + ' h 20 L ' + (CAx - SVGx + 40) + ' ' + (HDy - SVGy) + ' L ' + (HDl - SVGx - 2) + ' ' + (HDy - SVGy)).stroke({width: 1}).fill('none');
        line.marker('end', 20, 20, function(add) {
            add.polyline('0,5 10,10 0,15');
            this.fill('none');
            this.stroke({color: 'black', width: 1, linecap: 'round'});
        });
    }
    for (var itr = 1; itr <= numCQ; itr += 1) {
        var elemCQ = $('.visCanvas .right :nth-child(' + (itr) + ')');
        CQx = elemCQ.position()['left'];
        var CQy = elemCQ.position()['top'] + (elemCQ.height() / 2);
        var line = draw.path('M ' + (HDr - SVGx + 2) + ' ' + (HDy - SVGy) + ' L ' + (CQx - SVGx - 40) + ' ' + (HDy - SVGy) + ' L ' + (CQx - SVGx - 20) + ' ' + (CQy - SVGy) + ' h 18').stroke({width: 1}).fill('none');
        line.marker('end', 20, 20, function(add) {
            add.polyline('0,5 10,10 0,15');
            this.fill('none');
            this.stroke({color: 'black', width: 1, linecap: 'round'});
        });
    }
    
    var PCmin = CAx + 40;
    var PCmax = HDl - SVGx;
    for (var itr = 0; itr < numPC; itr += 1) {
        var elemPC = $('.visCanvas .between .mid-left >:nth-child(' + (itr + 1) + ')');
        elemPC.css('width', (100 / (numMC + 2)) + '%');
        elemPC.offset({
            top: HDy + DLGy + SVGy + 16,
            left: PCmin + DLGx + SVGx - elemPC.width() / 2 + (PCmax - PCmin) * (itr + 1) / (numPC + 1)
        });
        var elemCS = elemPC.find('.status a');
        var elemMK = elemPC.find('.marker');
        if (elemCS.html() === "Adopted") {
            elemCS.parent().css('background', '#6ebe49');
            elemCS.parent().css('border', '1px solid #52A928');
            elemMK.css('background', 'linear-gradient(#97DD75, #52A928)');
        } else if (elemCS.html() === "Rejected") {
            elemCS.parent().css('background', '#d61d3c');
            elemCS.parent().css('border', '1px solid #AA0D26');
            elemMK.css('background', 'linear-gradient(#E2475F, #AA0D26)');
        } else {
            elemCS.parent().css('background', '#f2bb47');
            elemCS.parent().css('border', '1px solid #E4A31A');
            elemMK.css('background', 'linear-gradient(#FFD06D, #E4A31A)');
        }
    }
    
    var MCmin = HDr - SVGx;
    var MCmax = CQx - 40;
    for (var itr = 0; itr < numMC; itr += 1) {
        var elemMC = $('.visCanvas .between .mid-right >:nth-child(' + (itr + 1) + ')');
        elemMC.css('width', (100 / (numMC + 2)) + '%');
        elemMC.offset({
            top: HDy + DLGy + SVGy + 16,
            left: MCmin + DLGx + SVGx - elemMC.width() / 2 + (MCmax - MCmin) * (itr + 1) / (numMC + 1)
        });
        var elemCS = elemMC.find('.status a');
        var elemMK = elemMC.find('.marker');
        if (elemCS.html() === "Adopted") {
            elemCS.parent().css('background', '#6ebe49');
            elemCS.parent().css('border', '1px solid #52A928');
            elemMK.css('background', 'linear-gradient(#97DD75, #52A928)');
        } else if (elemCS.html() === "Rejected") {
            elemCS.parent().css('background', '#d61d3c');
            elemCS.parent().css('border', '1px solid #AA0D26');
            elemMK.css('background', 'linear-gradient(#E2475F, #AA0D26)');
        } else {
            elemCS.parent().css('background', '#f2bb47');
            elemCS.parent().css('border', '1px solid #E4A31A');
            elemMK.css('background', 'linear-gradient(#FFD06D, #E4A31A)');
        }
    }
}