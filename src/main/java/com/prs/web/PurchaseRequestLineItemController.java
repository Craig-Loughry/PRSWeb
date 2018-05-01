package com.prs.web;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prs.business.purchaserequest.PurchaseRequest;
import com.prs.business.purchaserequest.PurchaseRequestLineItem;
import com.prs.business.purchaserequest.PurchaseRequestLineItemRepository;
import com.prs.business.purchaserequest.PurchaseRequestRepository;
import com.prs.util.PRSMaintenanceReturn;

@Controller 
@RequestMapping(path = "/PurchaseRequestLineItemLineItemController") 
public class PurchaseRequestLineItemController extends BaseController {
	@Autowired 
	private PurchaseRequestLineItemRepository purchaseRequestLineItemRepository;
	@Autowired
	private PurchaseRequestRepository prRepository; 

	
	@GetMapping(path = "/List")
	public @ResponseBody Iterable<PurchaseRequestLineItem> get() {
		return purchaseRequestLineItemRepository.findAll();
		
	}
	
	@GetMapping(path="/LinesForPR")
	public @ResponseBody Iterable<PurchaseRequestLineItem> getAllPurchaseRequestLineItems(@RequestParam int id) {
		return purchaseRequestLineItemRepository.findAllByPurchaseRequestId(id);
		
	}
	
	@GetMapping(path = "/Get")
	public @ResponseBody List<PurchaseRequestLineItem> getVendor(@RequestParam int id) {
		Optional<PurchaseRequestLineItem> purchaseRequestLineItem = purchaseRequestLineItemRepository.findById(id);
		return getReturnArray(purchaseRequestLineItem);
		
	}
	@PostMapping(path = "/Add")
	public @ResponseBody PRSMaintenanceReturn addNewPurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem purchaserequestLineItem) {
		try {
			purchaseRequestLineItemRepository.save(purchaserequestLineItem);
			updatePRTotal(purchaserequestLineItem);
			return PRSMaintenanceReturn.getMaintReturn(purchaserequestLineItem);
		} catch (DataIntegrityViolationException dive) {
			return PRSMaintenanceReturn.getMaintReturnError(purchaserequestLineItem, dive.getRootCause().toString());
		} catch (Exception e) {
			purchaserequestLineItem = null;
			e.printStackTrace();
			return PRSMaintenanceReturn.getMaintReturnError(purchaserequestLineItem, e.getMessage());
		}
	}

	@GetMapping(path = "/Remove") 
	public @ResponseBody PRSMaintenanceReturn deletePurchaseRequestLineItem(@RequestParam int id) {

		Optional<PurchaseRequestLineItem> purchaserequestLineItem = purchaseRequestLineItemRepository.findById(id);
		try {
			purchaseRequestLineItemRepository.delete(purchaserequestLineItem.get());
			updatePRTotal(purchaserequestLineItem.get());
			return PRSMaintenanceReturn.getMaintReturn(purchaserequestLineItem.get());
		} catch (DataIntegrityViolationException dive) {
			return PRSMaintenanceReturn.getMaintReturnError(purchaserequestLineItem, dive.getRootCause().toString());
		} catch (Exception e) {
			return PRSMaintenanceReturn.getMaintReturnError(purchaserequestLineItem, e.toString());
		}

	}

	@PostMapping(path="/Change") 
		public @ResponseBody PRSMaintenanceReturn updatePurchaseRequest(@RequestBody PurchaseRequestLineItem purchaserequestLineItem) {
			try {
				purchaseRequestLineItemRepository.save(purchaserequestLineItem);
				updatePRTotal(purchaserequestLineItem);
				return PRSMaintenanceReturn.getMaintReturn(purchaserequestLineItem);
			}
			catch (DataIntegrityViolationException dive) {
				return PRSMaintenanceReturn.getMaintReturnError(purchaserequestLineItem, dive.getRootCause().toString());
			}
			catch (Exception e) {
				return PRSMaintenanceReturn.getMaintReturnError(purchaserequestLineItem, e.toString());
			}
			
	}
	public void updatePRTotal(PurchaseRequestLineItem purchaseRequestLineItem) {
		Optional<PurchaseRequest> pr0pt = prRepository.findById(purchaseRequestLineItem.getPurchaseRequest().getId());
		PurchaseRequest pr = pr0pt.get();	
		List<PurchaseRequestLineItem> prlist = new ArrayList<>();
		prlist = purchaseRequestLineItemRepository.findAllByPurchaseRequestId(pr.getId());
		double total = 0; 
		for (PurchaseRequestLineItem prli : prlist) {
			total += prli.getProduct().getPrice()*prli.getQuantity();		
		}
		pr.setTotal(total);
		prRepository.save(pr);
	}
	}
