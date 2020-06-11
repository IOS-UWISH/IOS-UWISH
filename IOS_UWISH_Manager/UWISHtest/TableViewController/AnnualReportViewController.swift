//
//  AnnualReportViewController.swift
//  UWISHtest
//
//  Created by 洪郁茹 on 2020/6/7.
//  Copyright © 2020 Nancy. All rights reserved.
//

import UIKit
import Charts

/*只差如何刷新畫面...新取的值有確實輸入*/

class AnnualReportViewController: UIViewController {
    @IBOutlet weak var myView: BarChartView!
    @IBOutlet weak var lbYear: UILabel!
    
    var axisFormatDelgat: IAxisValueFormatter?
    var month = Month()
    var incomeArray:[Double] = []
    var requestParam = ["action" : "getAll2020"]
    
    let year = ["2019年","2020年","2021年"]
    
    var num = 1
    
    override func viewDidLoad() {
        super.viewDidLoad()
        showMonths(requestParam)
        //        showTest()
        lbYear.text = year[1]
    }
    
    var monthArray = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
    
    //這邊要建立方法去server拿資料...
    let url_server = URL(string: common_url + "MonthServlet")
    func showMonths(_ requestParam: [String: String]) {
        executeTask(url_server!, requestParam) {(data,response,error) in
            if error == nil {
                if data != nil {
                    print("input:\(String(describing: String(data: data!, encoding: .utf8)))")
                    
                    self.incomeArray.removeAll()//清空陣列的資料，再重新加入新的資料...！！！我的老天鵝...Peter顯靈
                    
                    if let result = try? JSONDecoder().decode(Month.self, from: data!){
                        self.month = result
                        self.incomeArray.append(self.month.Jan ?? 0)
                        self.incomeArray.append(self.month.Feb ?? 0)
                        self.incomeArray.append(self.month.Mar ?? 0)
                        self.incomeArray.append(self.month.Apr ?? 0)
                        self.incomeArray.append(self.month.May ?? 0)
                        self.incomeArray.append(self.month.Jun ?? 0)
                        self.incomeArray.append(self.month.Jly ?? 0)
                        self.incomeArray.append(self.month.Aug ?? 0)
                        self.incomeArray.append(self.month.Sep ?? 0)
                        self.incomeArray.append(self.month.Oct ?? 0)
                        self.incomeArray.append(self.month.Nov ?? 0)
                        self.incomeArray.append(self.month.Dec ?? 0)//生成一個存放資料的陣列，型別是BarChartDataEntry.
                        
                        //呼叫主執行緒...把畫面呈現
                        DispatchQueue.main.async {
                            
                            let monthArray = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
                            var dataEntries: [BarChartDataEntry] = []
                            //實作一個迴圈，來存入每筆顯示的資料內容
                            for i in 0 ..< self.monthArray.count {
                                //需設定x, y座標分別需顯示什麼東西;y:替換成自己的資料...
                                let dataEntry = BarChartDataEntry(x:Double(i),  y: self.incomeArray[i])
                                print(self.incomeArray[i])
                                //最後把每次生成的dataEntry存入到dataEntries當中
                                dataEntries.append(dataEntry)
                                
                            }
                            //透過BarChartDataSet設定我們要顯示的資料為何，以及圖表下方的label
                            let chartDataSet = BarChartDataSet(entries: dataEntries, label: "incomeArray per month")
                            //把整個dataset轉換成可以顯示的BarChartData
                            let charData = BarChartData(dataSet: chartDataSet)
                            
                            //最後在指定剛剛連結的myView要顯示的資料為charData
                            self.myView.data = charData
                            self.myView.xAxis.valueFormatter = IndexAxisValueFormatter(values: monthArray)
                            self.myView.xAxis.granularity = 1
                        }
                        
                    }
                }else {
                    print(error!.localizedDescription)
                }
            }
        }
    }
    
    @IBAction func btLeft(_ sender: Any) {
        num -= 1
        if num < 0 {
            num = year.count - 1
        }
        
        lbYear.text = year[num]
        
        if num == 0 {
            requestParam = ["action" : "getAll2019"]
            showMonths(requestParam)
        }
        if num == 1 {
            requestParam = ["action" : "getAll2020"]
            showMonths(requestParam)
        }
        if num == 2 {
            requestParam = ["action" : "getAll2021"]
            showMonths(requestParam)
        }
        
    }
    
    @IBAction func btRight(_ sender: Any) {
        num += 1
        if num >= year.count{
            num = 0
        }
        
        lbYear.text = year[num]
        
        if num == 0 {
            requestParam = ["action" : "getAll2019"]
            showMonths(requestParam)
        }
        if num == 1 {
            requestParam = ["action" : "getAll2020"]
            showMonths(requestParam)
        }
        if num == 2 {
            requestParam = ["action" : "getAll2021"]
            showMonths(requestParam)
        }
        
    }
    
    //測試用的方法...下面這邊是直接抓取Asser的欓案做使用
//    func showTest()  {
//         self.incomeArray.removeAll()//清空陣列的資料，再重新加入新的資料...！！！我的老天鵝...Peter顯靈
//        if let data = NSDataAsset(name: "getAll2019")?.data,let result = try? JSONDecoder().decode(Month.self, from: data){
//            self.month = result
//            self.incomeArray.append(self.month.Jan ?? 0)
//            self.incomeArray.append(self.month.Feb ?? 0)
//            self.incomeArray.append(self.month.Mar ?? 0)
//            self.incomeArray.append(self.month.Apr ?? 0)
//            self.incomeArray.append(self.month.May ?? 0)
//            self.incomeArray.append(self.month.Jun ?? 0)
//            self.incomeArray.append(self.month.Jly ?? 0)
//            self.incomeArray.append(self.month.Aug ?? 0)
//            self.incomeArray.append(self.month.Sep ?? 0)
//            self.incomeArray.append(self.month.Oct ?? 0)
//            self.incomeArray.append(self.month.Nov ?? 0)
//            self.incomeArray.append(self.month.Dec ?? 0)//生成一個存放資料的陣列，型別是BarChartDataEntry.
//            let monthArray = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
//            var dataEntries: [BarChartDataEntry] = []
//            //實作一個迴圈，來存入每筆顯示的資料內容
//            for i in 0 ..< self.monthArray.count {
//                //需設定x, y座標分別需顯示什麼東西;y:替換成自己的資料...
//                let dataEntry = BarChartDataEntry(x:Double(i),  y: self.incomeArray[i])
//                //最後把每次生成的dataEntry存入到dataEntries當中
//                dataEntries.append(dataEntry)
//            }
//            //透過BarChartDataSet設定我們要顯示的資料為何，以及圖表下方的label
//            let chartDataSet = BarChartDataSet(entries: dataEntries, label: "incomeArray per month")
//            //把整個dataset轉換成可以顯示的BarChartData
//            let charData = BarChartData(dataSet: chartDataSet)
//            //最後在指定剛剛連結的myView要顯示的資料為charData
//            self.myView.data = charData
//            self.myView.xAxis.valueFormatter = IndexAxisValueFormatter(values: monthArray)
//            self.myView.xAxis.granularity = 1
//
//        }
//    }
    
    
}
