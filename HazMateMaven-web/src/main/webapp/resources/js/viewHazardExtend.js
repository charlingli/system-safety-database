/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var nLoaded = 0;
var nHazards = 0;

var inputIds = ['HD', 'SP', 'SL', 'SG', 'SN', 'SH', 'HC', 'HA', 'HT', 
    'HO', 'CA', 'CQ', 'RC', 'CD', 'CH', 'CT', 'CO', 'CR', 'CJ', 'CM'];

function globalFilter() {
    gInput = $("#gInput").val().toLowerCase();
    gRows = $(".extendTable >tbody >tr");
    for (i = 0; i < gRows.length; i ++ ) {
        gData = gRows[i].innerText.toLowerCase();
        if (gData.indexOf(gInput) > -1) {
            gRows[i].style.display = "";
        } else {
            gRows[i].style.display = "none";
        }
    }
}

function filterRows() {
    clearRows = new Array();
    tRows = $(".extendTable >tbody >tr");
    for (i = 0; i < inputIds.length; i ++) {
        tDataInput = ".extendTable #" + inputIds[i] + "Input";
        tInput = $(tDataInput).val().toLowerCase();
        if (tInput.length > 0) {
            tDataSelect = ".extendTable ." + inputIds[i] + "Data";
            tCols = $(tDataSelect);
            for (j = 0; j < tRows.length; j ++ ) {
                tData = tCols[j].innerText.toLowerCase();
                if (tData.indexOf(tInput) === -1) {
                    clearRows.push(j);
                }
            }
        }
    }
    for (i = 0; i < tRows.length; i ++) {
        if (clearRows.includes(i)) {
            tRows[i].style.display = "none";
        } else {
            tRows[i].style.display = "";
        }
    }
}

function toggleColumn(id) {
    
}

window.onload = function() {
    $("#progress-modal").css("transition", "opacity .5s, top .5s");
    $("#progress-modal").css("opacity", "0");
    $("#progress-modal").css("top", "-100%");
}

