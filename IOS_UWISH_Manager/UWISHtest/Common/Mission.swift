//
//  Mission.swift
//  UWISHtest
//
//  Created by 洪郁茹 on 2020/6/8.
//  Copyright © 2020 Nancy. All rights reserved.
//

import Foundation

class Mission: Codable {
    var userId: Int!
    var userName: String!
    var title: String!
    var reportId: Int!
    var CommentDetail: String!
    
    init(userId: Int? = nil, userName: String? = nil, title: String? = nil, reportId: Int? = nil ) {
        self.userId = userId
        self.userName = userName
        self.title = title
        self.reportId = reportId
    }
    
    init(reportId: Int? = nil, userId: Int? = nil, userName: String? = nil, CommentDetail: String? = nil) {
        self.userId = userId
        self.userName = userName
        self.CommentDetail = CommentDetail
        self.reportId = reportId
    }
    
    public func info() -> String {
        return "userId: \(userId!),userName: \(userName!),title: \(title!),reportId: \(reportId!)"
    }
    
    public static func info(_ missions:[Mission]) -> String {
        var text = ""
        for mission in missions {
            text += mission.info() + "\n"
        }
        return text
    }
    
    
}
