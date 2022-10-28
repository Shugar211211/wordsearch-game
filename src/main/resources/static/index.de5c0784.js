const submitWordBtn = document.querySelector(".submit-word");
submitWordBtn.addEventListener("click", async ()=>{
    let result = await fetchGridInfo([
        "ONE",
        "TWO",
        "THREE"
    ]);
    console.log(result);
});
async function fetchGridInfo(wordList) {
    const commaSeparatedWords = wordList.join(",");
    // let response = await fetch(`https://jb-word-search-game-api.herokuapp.com/wordgrid?gridSize=20&words=${commaSeparatedWords}`);
    let response = await fetch(`http://localhost:8080/wordgrid?gridSize=20&words=${commaSeparatedWords}`);
    let result = await response.text();
    return result.split(" ");
}

//# sourceMappingURL=index.de5c0784.js.map
