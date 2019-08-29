package info.moroff.prescriptionmanager.settings;

public class UserSettings {

	private Boolean createNewTherapy = false;
	
	private Boolean showInactiveDrugs = false;

	private Boolean deleteTherapy = false;
	
	public Boolean getCreateNewTherapy() {
		return createNewTherapy;
	}
	public void setCreateNewTherapy(Boolean createNewTherapy) {
		this.createNewTherapy = createNewTherapy;
	}

	public Boolean getShowInactiveDrugs() {
		return showInactiveDrugs;
	}
	public void setShowInactiveDrugs(Boolean showInactiveDrugs) {
		this.showInactiveDrugs = showInactiveDrugs;
	}
	
	public Boolean getDeleteTherapy() {
		return deleteTherapy;
	}
	public void setDeleteTherapy(Boolean deleteTherapy) {
		this.deleteTherapy = deleteTherapy;
	}
	
	
}
