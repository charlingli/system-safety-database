/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function buildValidation() {
    let headers = $("#hazardsForm\\:hazardsTable_head tr").eq(0).children();
    let data = $("#hazardsForm\\:hazardsTable_data tr");
    
    let colLength = headers.length;
    let rowLength = data.length;
    
    for (let i = 0; i < rowLength; i ++) {
        let warning = $("#hazardsForm\\:hazardsTable\\:" + i + "\\:warningTooltip");
        let warnings = warning.text().split(". ");
        for (let j = 0; j < warnings.length; j ++) {
            $(findCell(warnings[j].split(" ")[5], i)).parent().css("background", "#f2bb47");
        }
        let error = $("#hazardsForm\\:hazardsTable\\:" + i + "\\:errorTooltip");
        let errors = error.text().split(". ");
        for (let j = 0; j < errors.length; j ++) {
            if (errors[j].startsWith("Missing")) {
                console.log(errors[j].split(" ")[5]);
                $(findCell(errors[j].split(" ")[5], i)).parent().css("background", "#d61d3c");
            } else if (errors[j].startsWith("Item")) {
                $(findCell(errors[j].split(" ")[6], i)).parent().css("background", "#d61d3c");
            }
        }
    }
}

function findCell(cellHeader, rowIndex) {
    switch(cellHeader) {
        case "cause":
        case "consequence":
        case "control":
            return "#hazardsForm\\:hazardsTable\\:" + rowIndex + "\\:RDCell";
            break;
        case "hazard":
            return "#hazardsForm\\:hazardsTable\\:" + rowIndex + "\\:HDCell";
            break;
        case "hazardworkshop":
            return "#hazardsForm\\:hazardsTable\\:" + rowIndex + "\\:HWCell";
            break;
        case "hazarddate":
            return "#hazardsForm\\:hazardsTable\\:" + rowIndex + "\\:DACell";
            break;
        case "hazardcontext":
            return "#hazardsForm\\:hazardsTable\\:" + rowIndex + "\\:HCCell";
            break;
        case "hazardlocation":
            return "#hazardsForm\\:hazardsTable\\:" + rowIndex + "\\:HLCell";
            break;
        case "hazardactivity":
            return "#hazardsForm\\:hazardsTable\\:" + rowIndex + "\\:HACell";
            break;
        case "hazardtype":
            return "#hazardsForm\\:hazardsTable\\:" + rowIndex + "\\:HTCell";
            break;
        case "hazardstatus":
            return "#hazardsForm\\:hazardsTable\\:" + rowIndex + "\\:HSCell";
            break;
        case "hazardowner":
            return "#hazardsForm\\:hazardsTable\\:" + rowIndex + "\\:HOCell";
            break;
        case "hazardriskclass":
            return "#hazardsForm\\:hazardsTable\\:" + rowIndex + "\\:RCCell";
            break;
        case "hazardcurrentfreq":
            return "#hazardsForm\\:hazardsTable\\:" + rowIndex + "\\:CFCell";
            break;
        case "hazardcurrentsev":
            return "#hazardsForm\\:hazardsTable\\:" + rowIndex + "\\:CSCell";
            break;
        case "hazardtargetfreq":
            return "#hazardsForm\\:hazardsTable\\:" + rowIndex + "\\:TFCell";
            break;
        case "hazardtargetsev":
            return "#hazardsForm\\:hazardsTable\\:" + rowIndex + "\\:TSCell";
            break;
        case "hazardsbs":
            return "#hazardsForm\\:hazardsTable\\:" + rowIndex + "\\:SCBtn";
            break;
        case "relationdescription":
            return "#hazardsForm\\:hazardsTable\\:" + rowIndex + "\\:RDCell";
            break;
        case "controltype":
            return "#hazardsForm\\:hazardsTable\\:" + rowIndex + "\\:CTCell";
            break;
        case "controlrecommend":
            return "#hazardsForm\\:hazardsTable\\:" + rowIndex + "\\:CRCell";
            break;
        case "controljustify":
            return "#hazardsForm\\:hazardsTable\\:" + rowIndex + "\\:CJCell";
            break;
        case "controlstatus":
            return "#hazardsForm\\:hazardsTable\\:" + rowIndex + "\\:CUCell";
            break;
    }
}