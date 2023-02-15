/**
 * @param {string} s
 * @param {string} target
 * @return {number}
 */
var rearrangeCharacters = function (s, target) {
    let res = 0;
    let count = 0
    do{
        for (let i = 0; i < target.length; i++) {
            if(s.indexOf(target[i])>=0){
                count++
                s = s.replace(target[i],"*")
            }
        }
        if(count==target.length){
            res++
        }
        count=0
        if(s.indexOf(target[0])<0){
            break
        }
    }while(true)
    return res;
};

console.log(rearrangeCharacters("scekucoovqleyrusfyenqbdqejffkwhlqjcigurieujtrcqufssefvtxtksbacbuysiugysntuap"
,"jujphzzvm"))