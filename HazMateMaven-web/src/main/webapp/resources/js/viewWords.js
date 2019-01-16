/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function viewWords() {
    var formReference = "";
    if ($('#relationForm').length > 0) {
        formReference = "#relationForm\\:";
    } else if ($('#editForm').length > 0) {
        formReference = "#editForm\\:";
    } else if ($('#addForm').length > 0) {
        formReference = "#addForm\\:";
    }
    
    var numDuplicates = $(formReference + 'similarityTable_data tr').length;
    
    let sharedWordsElem = $(formReference + 'similarityTable tr:first-child td:nth-last-child(2)');
    let sharedWordsText = sharedWordsElem.text().split(" ");
    
    let commonWordsElem = $(formReference + 'similarityTable tr:first-child td:last-child');
    let commonWordsText = commonWordsElem.text().split(" ");
    
    for (let i = 1; i <= numDuplicates; i ++) {
        let descriptionElem = $(formReference + 'similarityTable tr:nth-child(' + i + ') td:nth-last-child(4)');
        let descriptionText = descriptionElem.text().split(" ");
        
        let highlightedText = "";
        for (let j = 0; j < descriptionText.length; j ++) {
            let cleanText = descriptionText[j].replace("/", " ").replace("[^A-z\\s\\d\\-][\\\\\\^]?", "").replace(",", "").replace(".", "").toLowerCase();
            if (sharedWordsText.indexOf(cleanText) > -1) {
                highlightedText += "<span style='color:red;'>" + descriptionText[j] + '</span> ';
            } else if (commonWordsText.indexOf(cleanText) > -1) {
                highlightedText += descriptionText[j] + ' ';
            } else {
                console.log(descriptionText[j]);
                highlightedText += descriptionText[j] + ' ';
            }
        }
        descriptionElem.html(highlightedText);
    }
}