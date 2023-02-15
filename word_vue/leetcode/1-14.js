var countDifferentSubsequenceGCDs = function(nums) {
    let tmp = new Set()
    for (let i = 0; i < nums.length; i++) {
        let j = Math.floor(nums[i]/2)
        tmp.add(nums[i])
        while(j>=0){
            if(nums[i]%j==0){
                tmp.add(j)
                break;
            }else{
                j--;
            }
        }
    }
    return tmp.size
};

let c = countDifferentSubsequenceGCDs([5,15,40,5,6])
console.log(c)