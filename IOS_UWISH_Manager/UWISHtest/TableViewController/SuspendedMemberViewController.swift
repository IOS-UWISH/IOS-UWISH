//
//  SuspendedMemberViewController.swift
//  UWISHtest
//
//  Created by 洪郁茹 on 2020/6/8.
//  Copyright © 2020 Nancy. All rights reserved.
//

import UIKit

class SuspendedMemberViewController: UIViewController,UITableViewDelegate,UITableViewDataSource,UISearchBarDelegate {
    
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var searchBar: UISearchBar!
    
    let url_server = URL(string: common_url + "UserServlet")
    var requestParam = ["action" : "getAllUser"]
    var users:[User] = [] //儲存getAllUser的所有資料
    var userList:[User] = [] //儲存篩選user.status == true的資料(1.正常用戶)
    var searchList:[User] = [] //儲存searchBar篩選後資料
    let refreshControl = UIRefreshControl()//下拉功能的元件
    
    var test:String = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.tableView.delegate = self
        self.tableView.dataSource = self
        self.searchBar.delegate = self
        showUsers(requestParam)
        searchList = userList
        
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
    
    @objc func showRefresh(){
        showUsers(["action" : "getAllUser"])
        self.refreshControl.endRefreshing()
    }
    
    //seatchBar如下：
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        test = searchBar.text ?? ""
        // 如果搜尋條件為空字串，就顯示原始資料；否則就顯示搜尋後結果
        if test == "" {
            searchList = userList
        }else{
            // 搜尋原始資料內有無包含關鍵字(不區別大小寫)
            searchList = userList.filter({ (User) -> Bool in
                return User.name.uppercased().contains(test.uppercased())
            })
        }
        self.tableView.reloadData() //Q.沒有刷新?
    }
    
    // 點擊鍵盤上的Search按鈕時將鍵盤隱藏
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        searchBar.resignFirstResponder()
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if test == ""{
            return userList.count
        }else{
            return searchList.count
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cellId = "SuspendedMemberCell"
        
        let cell = tableView.dequeueReusableCell(withIdentifier: cellId) as! SuspendedMemberTableViewCell
        
        
        var user:User = User()
        
        if test == ""{
            user = userList[indexPath.row]
        }else{
            user = searchList[indexPath.row]
        }
        
        //尚未取得圖片，另外開啟task請求
        var requestParam = [String: Any]()
        requestParam["action"] = "getPhoto"
        requestParam["userId"] = user.uuid
        // 圖片寬度為tableViewCell的1/4，ImageView的寬度也建議在storyboard加上比例設定的constraint
        requestParam["imageSize"] = cell.frame.width / 4
        var image: UIImage?
        executeTask(url_server!, requestParam) { (data, response, error) in
            if error == nil {
                if data != nil {
                    image = UIImage(data: data!)
                }
                if image == nil {
                    image = UIImage(named: "noImage.jpg")
                }
                DispatchQueue.main.async { cell.ivUser.image = image }
            } else {
                print(error!.localizedDescription)
            }
        }
        
        //        cell.ivUser.image = user.image
        cell.lbUserName.text = user.name
        //addTarget點擊事件監聽器,self是被點擊到的按鈕.action放要處理事件的方法.for:觸發條件
        cell.btManage.tag = indexPath.row
        cell.btManage.addTarget(self, action: #selector(clickManage(sender:)), for: .touchUpInside)
        return cell
    }
    
    //點擊事件處理的方法如下：
    @objc func clickManage(sender:UIButton) { //sender:UIButton
        print("停權有被觸發到！") //toast
        var btrow:Int = 0
        var id : Int = 0
        var name : String = ""
        var status : Bool = false
        let usertest:User = User()
        if  test == ""{
            btrow = sender.tag
            print(btrow)
            id = userList[btrow].uuid
            name = userList[btrow].name
            status = userList[btrow].status
            usertest.uuid = id
            usertest.name = name
            usertest.status = status
        }else{
            btrow = sender.tag
            print(btrow)
            id = searchList[btrow].uuid
            name = searchList[btrow].name
            status = searchList[btrow].status
            usertest.uuid = id
            usertest.name = name
            usertest.status = status
        }
        
        var requestParam = [String: Any]()
        requestParam["action"] = "userUpdateStatus"
        requestParam["user"] = try? String(data: JSONEncoder().encode(usertest.self),encoding: .utf8)
        executeTask(url_server!, requestParam) { (data, response, error) in
            if error == nil {
                if data != nil {
                    if let result = String(data: data!, encoding: .utf8) {
                        if let count = Int(result) {
                            if count != 0{
                                print("修改成功")
                                /* 下面三行：刪除原陣列的內容，重新呼叫方法，刷新畫面...*/
                                self.userList.removeAll()
                                self.searchList.removeAll()
                                let requestParamRefresh = ["action" : "getAllUser"]
                                self.showUsers(requestParamRefresh)
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
    
    
    //抓server資料進來...貼至tableView上
    @objc func showUsers(_ requestParam: [String: String]){
        executeTask(url_server!, requestParam) { (data, response, error) in
            if error == nil{
                if data != nil {
                    print("input: \(String(data: data!, encoding: .utf8)!)")
                    
                    self.userList.removeAll()//若下拉刷新時，須先清空陣列...再重新放入新資料
                    
                    if let result = try? JSONDecoder().decode([User].self, from: data!) {
                        self.users = result
                        for user in self.users {
                            if user.status == false{
                                self.userList.append(user)
                            }
                        }
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
    
    
    
    
    
}
