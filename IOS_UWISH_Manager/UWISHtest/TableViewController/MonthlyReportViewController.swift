//
//  MonthlyReportViewController.swift
//  UWISHtest
//
//  Created by 洪郁茹 on 2020/6/7.
//  Copyright © 2020 Nancy. All rights reserved.
//

import UIKit
import Charts

/*只差如何刷新畫面...新取的值有確實輸入*/

class MonthlyReportViewController: UIViewController {
    
    @IBOutlet weak var myView: BarChartView!
    @IBOutlet weak var lbMonth: UILabel!
    
    var axisFormatDelgat: IAxisValueFormatter?
    var day = Day()
    var incomeArray:[Double] = []
    var requestParam = ["action" : "getAll202001"]
    
    let month = ["2020年01月","2020年02月","2020年03月","2020年04月","2020年05月","2020年06月"]
    
    var num = 0
    
    override func viewDidLoad() {
        super.viewDidLoad()
        showDays(requestParam)
        lbMonth.text = month[0]
    }
    
    var dayArray = ["1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"]
    
    //這邊要建立方法去server拿資料...
    let url_server = URL(string: common_url + "DayServlet")
    func showDays(_ requestParam: [String: String]) {
        executeTask(url_server!, requestParam) {(data,response,error) in
            if error == nil {
                if data != nil {
                    print("input:\(String(describing: String(data: data!, encoding: .utf8)))")
                    
                    self.incomeArray.removeAll()//清空陣列的資料，再重新加入新的資料...！！！我的老天鵝...Peter顯靈
                    
                    if let result = try? JSONDecoder().decode(Day.self, from: data!){
                        self.day = result
                        self.incomeArray.append(self.day.one ?? 0)//生成一個存放資料的陣列，型別是BarChartDataEntry.
                        self.incomeArray.append(self.day.two ?? 0)
                        self.incomeArray.append(self.day.three ?? 0)
                        self.incomeArray.append(self.day.four ?? 0)
                        self.incomeArray.append(self.day.five ?? 0)
                        self.incomeArray.append(self.day.six ?? 0)
                        self.incomeArray.append(self.day.seven ?? 0)
                        self.incomeArray.append(self.day.eight ?? 0)
                        self.incomeArray.append(self.day.nine ?? 0)
                        self.incomeArray.append(self.day.ten ?? 0)
                        self.incomeArray.append(self.day.tenone ?? 0)
                        self.incomeArray.append(self.day.tentwo ?? 0)
                        self.incomeArray.append(self.day.tenthree ?? 0)
                        self.incomeArray.append(self.day.tenfour ?? 0)
                        self.incomeArray.append(self.day.tenfive ?? 0)
                        self.incomeArray.append(self.day.tensix ?? 0)
                        self.incomeArray.append(self.day.tenseven ?? 0)
                        self.incomeArray.append(self.day.teneight ?? 0)
                        self.incomeArray.append(self.day.tennine ?? 0)
                        self.incomeArray.append(self.day.twenty ?? 0)
                        self.incomeArray.append(self.day.twentyone ?? 0)
                        self.incomeArray.append(self.day.twentytwo ?? 0)
                        self.incomeArray.append(self.day.twentythree ?? 0)
                        self.incomeArray.append(self.day.twentyfour ?? 0)
                        self.incomeArray.append(self.day.twentyfive ?? 0)
                        self.incomeArray.append(self.day.twentysix ?? 0)
                        self.incomeArray.append(self.day.twentyseven ?? 0)
                        self.incomeArray.append(self.day.twentyeight ?? 0)
                        self.incomeArray.append(self.day.twentynine ?? 0)
                        self.incomeArray.append(self.day.thirty ?? 0)
                        self.incomeArray.append(self.day.thirtyone ?? 0)
                
                        //呼叫主執行緒...把畫面呈現
                        DispatchQueue.main.async {
                            
                            let dayArray = ["1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"]
                            var dataEntries: [BarChartDataEntry] = []
                                                        
                            //實作一個迴圈，來存入每筆顯示的資料內容
                            for i in 0 ..< self.dayArray.count {
                                //需設定x, y座標分別需顯示什麼東西;y:替換成自己的資料...
                                let dataEntry = BarChartDataEntry(x:Double(i),  y: self.incomeArray[i])
                                //最後把每次生成的dataEntry存入到dataEntries當中
                                dataEntries.append(dataEntry)
                            }
                            //透過BarChartDataSet設定我們要顯示的資料為何，以及圖表下方的label
                            let chartDataSet = BarChartDataSet(entries: dataEntries, label: "incomeArray per day")
                            //把整個dataset轉換成可以顯示的BarChartData
                            let charData = BarChartData(dataSet: chartDataSet)
                            //最後在指定剛剛連結的myView要顯示的資料為charData
                            self.myView.data = charData
                            self.myView.xAxis.valueFormatter = IndexAxisValueFormatter(values: dayArray)
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
            num = month.count - 1
        }
        
        lbMonth.text = month[num]
        
        if num == 0 {
            requestParam = ["action" : "getAll202001"]
            showDays(requestParam)
        }
        if num == 1 {
            requestParam = ["action" : "getAll202002"]
            showDays(requestParam)
        }
        if num == 2 {
            requestParam = ["action" : "getAll202003"]
            showDays(requestParam)
        }
        if num == 3 {
            requestParam = ["action" : "getAll202004"]
            showDays(requestParam)
        }
        if num == 4 {
            requestParam = ["action" : "getAll202005"]
            showDays(requestParam)
        }
        if num == 5 {
            requestParam = ["action" : "getAll202006"]
            showDays(requestParam)
        }
        
    }
    
    @IBAction func btRight(_ sender: Any) {
        num += 1
        if num >= month.count{
            num = 0
        }
        lbMonth.text = month[num]
        
        if num == 0 {
            requestParam = ["action" : "getAll202001"]
            showDays(requestParam)
        }
        if num == 1 {
            requestParam = ["action" : "getAll202002"]
            showDays(requestParam)
        }
        if num == 2 {
            requestParam = ["action" : "getAll202003"]
            showDays(requestParam)
        }
        if num == 3 {
            requestParam = ["action" : "getAll202004"]
            showDays(requestParam)
        }
        if num == 4 {
            requestParam = ["action" : "getAll202005"]
            showDays(requestParam)
        }
        if num == 5 {
            requestParam = ["action" : "getAll202006"]
            showDays(requestParam)
        }
        
    }
    
}
