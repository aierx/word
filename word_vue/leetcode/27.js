var maxProfit = function (prices) {
    var min = prices[0]
    var res = 0
    for (let i = 1; i < prices.length; i++) {
        if((prices[i]-min)>res)
            res =  prices[i]-min
        if(min>prices[i])
            min = prices[i]        
    }

};