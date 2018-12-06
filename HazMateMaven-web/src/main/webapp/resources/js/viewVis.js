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
    
    var elemHD = $('.visCanvas .mid>div:nth-child(2)');
    var HDl = elemHD.position()['left'];
    var HDr = elemHD.position()['left'] + elemHD.width();
    var HDy = elemHD.position()['top'] + (elemHD.height() / 2);
    
    var elemRS = $('.severity');
    elemRS.each(function () {
        if ($(this).html()[1] === "0") {
            $(this).css('background', '#FFEA64');
            $(this).parent().css('border', '1px solid #FFDD00');
        } else if ($(this).html()[1] === "1") {
            $(this).css('background', '#FFDD00');
            $(this).parent().css('border', '1px solid #FFB300');
        } else if ($(this).html()[1] === "2") {
            $(this).css('background', '#FFB300');
            $(this).parent().css('border', '1px solid #FF8000');
        } else if ($(this).html()[1] === "3") {
            $(this).css('background', '#FF8000');
            $(this).parent().css('border', '1px solid #FF5500');
        } else if ($(this).html()[1] === "4") {
            $(this).css('background', '#FF5500');
            $(this).parent().css('border', '1px solid #FF0000');
        } else if ($(this).html()[1] === "5") {
            $(this).css('background', '#FF0000');
            $(this).parent().css('border', '1px solid #9B0000');
        } else if ($(this).html()[1] === "6") {
            $(this).css('background', '#9B0000');
            $(this).parent().css('border', '1px solid #4C0000');
        }
    });
    
    var elemRF = $('.frequency');
    elemRF.each(function () {
        if ($(this).html()[1] === "0") {
            $(this).css('background', '#FFEA64');
            $(this).parent().css('border', '1px solid #FFDD00');
        } else if ($(this).html()[1] === "1") {
            $(this).css('background', '#FFDD00');
            $(this).parent().css('border', '1px solid #FFB300');
        } else if ($(this).html()[1] === "2") {
            $(this).css('background', '#FFB300');
            $(this).parent().css('border', '1px solid #FF8000');
        } else if ($(this).html()[1] === "3") {
            $(this).css('background', '#FF8000');
            $(this).parent().css('border', '1px solid #FF5500');
        } else if ($(this).html()[1] === "4") {
            $(this).css('background', '#FF5500');
            $(this).parent().css('border', '1px solid #FF0000');
        } else if ($(this).html()[1] === "5") {
            $(this).css('background', '#FF0000');
            $(this).parent().css('border', '1px solid #9B0000');
        } else if ($(this).html()[1] === "6") {
            $(this).css('background', '#9B0000');
            $(this).parent().css('border', '1px solid #4C0000');
        }
    });
    
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