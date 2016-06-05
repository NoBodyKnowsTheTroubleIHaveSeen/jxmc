package org.whh.controller;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.whh.flush.FlushStatus;
import org.whh.flush.RouterMangerHelper;
import org.whh.flush.WDBrowser;
import org.whh.web.CommonMessage;

@Controller
public class FlushController {
	private static FlushStatus flushStatus = new FlushStatus();

	private static boolean stopFlush = false;

	private static final int TYPE_START_FLUSH = 1;
	private static final int TYPE_STOP_FLUSH = 2;

	@RequestMapping("/flush")
	public String index(Model model) {
		model.addAttribute("flushStatus", flushStatus);
		return "flush/flush";
	}

	@RequestMapping("/doFlush")
	@ResponseBody
	public CommonMessage doFlush(Integer totalCount, boolean restartRouter, Integer type) {
		synchronized (flushStatus) {
			CommonMessage message = new CommonMessage();
			boolean isStart = flushStatus.getIsStart();
			if (type == TYPE_STOP_FLUSH) {
				stopFlush = true;
				if (isStart) {
					message.setMessage("正在停止,请稍后...");
				} else {
					message.setMessage("已停止");
				}
			} else if (type == TYPE_START_FLUSH) {
				stopFlush = false;
				flushStatus.setIsStart(true);
				if (isStart) {
					message.setMessage("程序已经启动了，不能再次启动");
					return message;
				}
				flushStatus.setTotalCount(totalCount);
				flushStatus.setIsStart(true);
				flushStatus.setIsStartStr("是");
				new Thread(new Runnable() {

					@Override
					public void run() {
						for (int i = 1; i <= totalCount; i++) {
							WDBrowser browser = new WDBrowser();
							browser.browse();
							if (stopFlush) {
								flushStatus.setBrowsedCount(i);
								flushStatus.setLeftedCount(0);
								break;
							}
							try {
								int sleepTime = new Random().nextInt(1000 * 60 * new Random().nextInt(5) + 1);
								flushStatus.setSleepTime(sleepTime / 1000 * 60);
								Thread.sleep(sleepTime);
								flushStatus.setSleepTime(0);
								if (restartRouter) {
									RouterMangerHelper.reConnect();
								}
								flushStatus.setBrowsedCount(i);
								flushStatus.setLeftedCount(totalCount - i);
							} catch (InterruptedException e) {
							}
						}
						flushStatus.setIsStart(false);
						flushStatus.setIsStartStr("否");
						flushStatus.setLeftedCount(0);
					}
				}).start();
				message.setMessage("启动成功");
			}

			return message;
		}
	}
}
