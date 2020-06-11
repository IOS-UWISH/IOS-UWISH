//
//  CommentReportViewController.swift
//  UWISHtest
//
//  Created by 洪郁茹 on 2020/6/8.
//  Copyright © 2020 Nancy. All rights reserved.
//

import UIKit

class CommentReportViewController: UIViewController,UITableViewDataSource,UITableViewDelegate {
    
    
    @IBOutlet weak var tableView: UITableView!
    
    let url_server = URL(string: common_url + "MissionServlet")
    var missions:[Mission] = [] //儲存getAllUser的所有資料
    let refreshControl = UIRefreshControl()//下拉功能的元件
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.tableView.dataSource = self
        self.tableView.delegate = self
        showComments()

        //開始下拉更新的功能如下：
               //修改顯示文字的顏色
               let attributes = [NSAttributedString.Key.foregroundColor:UIColor.white]
               //顯示文字內容
               refreshControl.attributedTitle = NSAttributedString(string: "正在更新", attributes: attributes)
               //設定元件顏色
               refreshControl.tintColor = UIColor.white
               //設定背景顏色
               refreshControl.backgroundColor = UIColor.black
               refreshControl.addTarget(self, action: #selector(showRefresh), for: UIControl.Event.valueChanged)
               tableView.refreshControl = refreshControl
    }
    
    //下拉刷新方法：
    @objc func showRefresh(){
        showComments()
        self.refreshControl.endRefreshing()
    }
    
    //tableView設定：
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return missions.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cellId = "CommentReportCell"
        let mission = missions[indexPath.row]
        /* 將cell轉型成BookCell */
        let cell = tableView.dequeueReusableCell(withIdentifier: cellId) as! CommentReportTableViewCell
        
        //        cell.ivUser.image = UIImage(named:"shark")
        
        // 尚未取得圖片，另外開啟task請求
        var requestParam = [String:Any]()
        requestParam["action"] = "getPhoto"
        requestParam["userId"] = mission.userId
        // 圖片寬度為tableViewCell的1/4，ImageView的寬度也建議在storyboard加上比例設定的constraint
        requestParam["imageSize"] = cell.frame.width / 4
        var image:UIImage?
        executeTask(url_server!, requestParam) { (data, response, error) in
            if error == nil {
                if data != nil {
                    image = UIImage(data: data!)
                }
                if image == nil {
                    image = UIImage(named: "noImage.jpg")
                }
                DispatchQueue.main.async {
                    cell.ivUser.image = image
                }
            }else{
                print(error!.localizedDescription)
            }
        }
        
        cell.lbUserName.text = mission.userName
        cell.lbTitle.text = mission.CommentDetail
        cell.btManage.tag = indexPath.row
        cell.btManage.addTarget(self, action: #selector(show(sender:)), for:.touchUpInside)
        
        return cell
    }
    
    //這邊要處理按鍵按到哪一個項目...
    @objc func show(sender:UIButton) {
        print("管理有觸發到！")
        
        let btrow:Int = sender.tag
        let reportId = missions[btrow].reportId!
        let userId = missions[btrow].userId!
        let CommentDetail = missions[btrow].CommentDetail
        print("reportId: \(reportId), userId: \(userId), CommentDetail: \(CommentDetail)")
        
        //actionSheet:
        let alertController = UIAlertController(title: "管理項目", message: "請選擇", preferredStyle:.actionSheet)
        
        let ignore = UIAlertAction(title: "忽略", style: .default) { (alertAction) in
            print("忽略有被觸發到！")
            self.updateReportStatus(reportId,userId,1,false)
        }
        let OffShelf = UIAlertAction(title: "下架", style: .default) { (alertAction) in
            print("下架有被觸發到！")
            self.updateReportStatus(reportId,userId,2,false)
        }
        let Suspension = UIAlertAction(title: "停權", style: .destructive) { (alertAction) in
            print("停權有被觸發到！")
            self.updateReportStatus(reportId,userId,3,false)
        }
        let cancel = UIAlertAction(title: "取消", style: .cancel) {
            (alertAction) in
        }
        alertController.addAction(ignore)
        alertController.addAction(OffShelf)
        alertController.addAction(Suspension)
        alertController.addAction(cancel)
        self.present(alertController, animated: true, completion:nil)
    }
    
    //取得被檢舉的留言：
    func showComments() {
        let requestParam = ["action" : "getCommentReport"]
        executeTask(url_server!, requestParam) { (data, response, error) in
            if error == nil{
                if data != nil{
                    // 將輸入資料列印出來除錯用
                    print("input: \(String(data: data!, encoding: .utf8)!)")
                    
                    if let result = try? JSONDecoder().decode([Mission].self, from: data!){
                        self.missions = result
                        DispatchQueue.main.async {
                            self.tableView.reloadData()
                        }
                    }
                }
            }else{
                print(error!.localizedDescription)
            }
        }
    }
    
    //修改檢舉留言的狀態：
    func updateReportStatus(_ reportId:Int,_ userId:Int,_ status:Int,_ isMission:Bool) {
        var requestParam = [String:Any]()
        requestParam["action"] = "updateReportStatus"
        requestParam["reportId"] = reportId
        requestParam["reportStatus"] = status
        requestParam["isMission"] = isMission
        requestParam["userId"] = userId
        executeTask(url_server!, requestParam) { (data, response, error) in
            if error == nil {
                if data != nil {
                    if let result = String(data: data!, encoding: .utf8) {
                        if let count = Int(result) {
                            if count != 0{
                                print("修改成功")
                                /* 刪除原陣列的內容，重新呼叫方法，刷新畫面...*/
                                self.missions.removeAll()
                                self.showComments()
                            }else{
                                print("修改失敗")
                            }
                        }
                    }
                }
            } else {
                print(error!.localizedDescription)
            }
        }
    }
    
}
