//
//  TaskReportTableViewCell.swift
//  UWISHtest
//
//  Created by 洪郁茹 on 2020/6/8.
//  Copyright © 2020 Nancy. All rights reserved.
//

import UIKit

class TaskReportTableViewCell: UITableViewCell {
    @IBOutlet weak var ivUser: UIImageView!
    @IBOutlet weak var lbUserName: UILabel!
    @IBOutlet weak var lbTitle: UILabel!
    @IBOutlet weak var btManage: UIButton!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
