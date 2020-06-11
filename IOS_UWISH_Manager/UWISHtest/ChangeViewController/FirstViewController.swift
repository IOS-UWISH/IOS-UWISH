//
//  ViewController.swift
//  UWISHtest
//
//  Created by 洪郁茹 on 2020/6/7.
//  Copyright © 2020 Nancy. All rights reserved.
//

import UIKit

class FirstViewController: UIViewController {
    
    @IBOutlet weak var firstView: UIView!
    @IBOutlet weak var secondView: UIView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }
    
    @IBAction func switchViews(_ sender:UISegmentedControl){
        if sender.selectedSegmentIndex == 0 {
            //            firstView.alpha = 1
            //            secondView.alpha = 0
            firstView.isHidden = false
            secondView.isHidden = true
            //Q.使用isHidden效果似乎好於alpha?
            
        }
        if sender.selectedSegmentIndex == 1 {
            //            firstView.alpha = 0
            //            secondView.alpha = 1
            firstView.isHidden = true
            secondView.isHidden = false
            
        }
        
        
    }
    
    
}

