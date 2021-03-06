package com.prs.web;

import java.sql.Timestamp;
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
import com.prs.business.purchaserequest.PurchaseRequestRepository;
import com.prs.util.PRSMaintenanceReturn;

@Controller 
@RequestMapping(path = "/PurchaseRequests") 
public class PurchaseRequestController extends BaseController {
	@Autowired 
	private PurchaseRequestRepository purchaseRequestRepository;


	@GetMapping(path = "/List")
	public @ResponseBody Iterable<PurchaseRequest> getAllPurchaseRequests() {
		return purchaseRequestRepository.findAll();
	}

	@GetMapping(path = "/Get")
	public @ResponseBody List<PurchaseRequest> getPurchaseRequest(@RequestParam int id) {
		Optional<PurchaseRequest> pr = purchaseRequestRepository.findById(id);
		return getReturnArray(pr.get());
	}
	@PostMapping(path = "/Add")
	public @ResponseBody PRSMaintenanceReturn addNewPurchaseRequest(@RequestBody PurchaseRequest purchaserequest) {
		try {
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			purchaserequest.setSubmittedDate(ts); 
			purchaserequest.setStatus(PurchaseRequest.STATUS_NEW);
			purchaseRequestRepository.save(purchaserequest);
			return PRSMaintenanceReturn.getMaintReturn(purchaserequest);
		} catch (DataIntegrityViolationException dive) {
			return PRSMaintenanceReturn.getMaintReturnError(purchaserequest, dive.getRootCause().toString());
		} catch (Exception e) {
			e.printStackTrace();
			return PRSMaintenanceReturn.getMaintReturnError(purchaserequest, e.getMessage());
		}
	}

	@GetMapping(path = "/Remove") 
	public @ResponseBody PRSMaintenanceReturn deletePurchaseRequest(@RequestParam int id) {

		Optional<PurchaseRequest> purchaserequest = purchaseRequestRepository.findById(id);
		try {
			purchaseRequestRepository.delete(purchaserequest.get());
			return PRSMaintenanceReturn.getMaintReturn(purchaserequest.get());
		} catch (DataIntegrityViolationException dive) {
			return PRSMaintenanceReturn.getMaintReturnError(purchaserequest, dive.getRootCause().toString());
		} catch (Exception e) {
			return PRSMaintenanceReturn.getMaintReturnError(purchaserequest, e.toString());
		}

	}

	@PostMapping(path="/Change") 
		public @ResponseBody PRSMaintenanceReturn updatePurchaseRequest (@RequestBody PurchaseRequest purchaserequest) {
			try {
				purchaseRequestRepository.save(purchaserequest);
				return PRSMaintenanceReturn.getMaintReturn(purchaserequest);
			}
			catch (DataIntegrityViolationException dive) {
				return PRSMaintenanceReturn.getMaintReturnError(purchaserequest, dive.getRootCause().toString());
			}
			catch (Exception e) {
				return PRSMaintenanceReturn.getMaintReturnError(purchaserequest, e.toString());
			}
			
	}
	@PostMapping(path = "/Submit")
    public @ResponseBody PRSMaintenanceReturn submitForReview(@RequestBody PurchaseRequest purchaseRequest) {
        	Optional<PurchaseRequest> prOpt = purchaseRequestRepository.findById(purchaseRequest.getId());
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            purchaseRequest = prOpt.get();
            if(purchaseRequest.getTotal() < 50.0) {
                purchaseRequest.setStatus(PurchaseRequest.STATUS_APPROVED);
            }else {
                purchaseRequest.setStatus(PurchaseRequest.STATUS_REVIEW);
            }
            purchaseRequest.setSubmittedDate(ts);
        try {
            purchaseRequestRepository.save(purchaseRequest);
            return PRSMaintenanceReturn.getMaintReturn(purchaseRequest);
        }
        catch (DataIntegrityViolationException dive) {
            return PRSMaintenanceReturn.getMaintReturnError(purchaseRequest, dive.getRootCause().toString());
        }
        catch (Exception e) {
            return PRSMaintenanceReturn.getMaintReturnError(purchaseRequest, e.toString());
        }
    }
	@GetMapping(path = "/GetRequestReview")
	public @ResponseBody Iterable<PurchaseRequest> getRequestReview(@RequestParam int id) {
		return purchaseRequestRepository.findAllByUserIdNot(id);
}
}
